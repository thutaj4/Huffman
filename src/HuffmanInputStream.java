import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;

public class HuffmanInputStream {
	// add additional private variables and methods as needed
	// DO NOT modify the public method signatures or add public methods
	private String tree;
	private int totalChars;
	private DataInputStream d;
	LinkedList<Integer> bits;
	int[] bit;
	int count;

	public HuffmanInputStream(String filename) throws IOException {
		try {
			d = new DataInputStream(new FileInputStream(filename));
			tree = d.readUTF();
			totalChars = d.readInt();

		} catch (IOException e) {
		}
		// add other initialization statements as needed
		bits = new LinkedList<>();
		count = 0;

	}

	public int readBit() throws IOException {
		// returns the next bit in the file
		// the value returned will be either a 0 or a 1
		if (bits.size() == 0) {
			if (d.available() > 0) {
				binary(d.readUnsignedByte());
			}

		}
		
		
		return bits.remove(bits.size() - 1);

	}

	private void binary(int val) {
		//System.out.println(val);
		while (val > 0) {
			int temp = val % 2;
			bits.add(temp);
			val = val / 2;
		}

		if (bits.size() < 8) {
			int diff = 8 - bits.size();
			for (int i = 0; i < diff; i++) {
				bits.add(0);
			}
		}
	}

	public String getTree() {
		// return the tree representation read from the file
		return tree;

	}

	public int getTotalChars() {
		// return the character count read from the file
		return totalChars;
	}
	
	public void close() throws IOException {
		d.close();
	}

}
