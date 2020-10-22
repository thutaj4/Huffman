import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * @author AJ Thut
 * Last Modified: 11/5/2019
 *
 */
public class HuffmanEncode {

	File infile;
	String outfile;
	int[] count;
	BinaryHeap<Integer, HuffmanTree> heap;
	HuffmanTree finaltree;
	String paths[];
	int numvals;
	String codebits;

	public HuffmanEncode(String in, String out) throws IOException {
		// Implements the Huffman encoding algorithm
		// Add private methods and instance variables as needed
		infile = new File(in);
		outfile = out;
		count = new int[128];
		heap = new BinaryHeap<>();
		numvals = 0;
		codebits = "";
		countChars();
		createTree();
	}

	public static void main(String args[]) throws IOException {
		// args[0] is the name of the file whose contents should be compressed
		// args[1] is the name of the output file that will hold the compressed
		// content of the input file
		new HuffmanEncode(args[0], args[1]);
		// do not add anything here
	}

	private void countChars() throws IOException {

		FileReader fr = new FileReader(infile);
		BufferedReader br = new BufferedReader(fr);

		int ch = br.read();
		while (ch != -1) {
			count[ch]++;
			numvals++;
			ch = br.read();
		}

		br.close();
	}

	private void createTree() throws IOException {
		// inserting all single node trees into the heap
		for (int i = 0; i < count.length; i++) {
			if (count[i] != 0) {
				heap.insert(count[i], new HuffmanTree((char) i));
			}
		}

		// remove/combine trees until only two trees remain in heap
		while ((heap.getSize() > 2)) {
			// get left subtree
			HuffmanTree left = heap.getMinOther();
			int prio = heap.getMinKey();
			heap.removeMin();
			// get right subtree
			HuffmanTree right = heap.getMinOther();
			prio += heap.getMinKey();
			heap.removeMin();

			// add two trees together into one new tree and insert back into the heap
			HuffmanTree temp = new HuffmanTree(left, right, (char) 128);
			heap.insert(prio, temp);
		}

		// get final tree and remove it from the heap
		finaltree = heap.getMinOther();
		heap.removeMin();

		// get paths of the leaf nodes
		paths = finaltree.pathsToLeaves();


		writeToFile();

	}

	private void writeToFile() throws IOException {
		HuffmanOutputStream d = new HuffmanOutputStream(outfile, finaltree.toString(), numvals);
		FileReader fr = new FileReader(infile);
		BufferedReader br = new BufferedReader(fr);
		String codebits = "";

		int ch = br.read();
		while (ch != -1) {
			for (int i = 0; i < paths.length; i++) {
				if (ch == paths[i].charAt(0)) {
					for (int j = 1; j < paths[i].length(); j++) {
						if (codebits.length() >= 8) {
							write(codebits, d);
							codebits = "";
						}
						codebits += paths[i].charAt(j);
					}
				}
			}

			ch = br.read();
		}

		br.close();


		if ((codebits.length() % 8) != 0) {
			if (codebits.length() < 8) {
				int rem = 8 - codebits.length();
				for (int i = 0; i < rem; i++) {
					codebits += "0";
				}
			} else {
				int rem = 8 - (codebits.length() % 8);
				for (int i = 0; i < rem; i++) {
					codebits += "0";
				}
			}
		}
		
		write(codebits, d);

		d.close();

	}

	private void write(String s, HuffmanOutputStream d) throws IOException {
		for (int i = 0; i < s.length(); i++) {
			d.writeBit(s.charAt(i));
		}
	}

}
