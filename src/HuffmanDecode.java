import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HuffmanDecode {

	HuffmanTree t;
	FileWriter fr;
	BufferedWriter br;
	PrintWriter pr;
	HuffmanInputStream input;
	int numvals;

	public HuffmanDecode(String in, String out) throws IOException {
		// implements the Huffman Decode Algorithm
		// Add private methods and instance variables as needed
		fr = new FileWriter(new File(out));
		br = new BufferedWriter(fr);
		pr = new PrintWriter(br);
		input = new HuffmanInputStream(in);
		toFile();
		input.close();
	}

	public static void main(String args[]) throws IOException {
		// args[0] is the name of a input file (a file created by Huffman Encode)
		// args[1] is the name of the output file for the uncompressed file
		new HuffmanDecode(args[0], args[1]);
		// do not add anything here
	}

	private void toFile() throws IOException {
		t = new HuffmanTree(input.getTree(), (char) 128);
		numvals = input.getTotalChars();
		while (numvals > 0) {
			int s = input.readBit();
			//System.out.println(s);
			if (s == 0) {
				t.moveToLeft();
				if (t.atLeaf()) {
					char c = t.current();
					pr.write(Character.toString(c));
					t.moveToRoot();
					numvals--;
				}

			} else {
				t.moveToRight();
				if (t.atLeaf()) {
					pr.write(Character.toString(t.current()));
					t.moveToRoot();
					numvals--;
				}
			}
		}
		pr.flush();
		pr.close();
		
	}
}
