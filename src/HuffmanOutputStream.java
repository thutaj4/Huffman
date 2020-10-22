import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class HuffmanOutputStream {
	// add additional private variables as needed
	// do not modify the public methods signatures or add public methods

	DataOutputStream d;
	int count;
	int s;

	public HuffmanOutputStream(String filename, String tree, int totalChars) {
		try {
			d = new DataOutputStream(new FileOutputStream(filename));
			d.writeUTF(tree);
			d.writeInt(totalChars);
		} catch (IOException e) {
			
		}
		// add other initialization statements as needed
		count = 0;
		s = 0;
	}

	public void writeBit(char bit) throws IOException{
		// PRE:bit == '0' || bit == '1'
		
	
		s *= 2;
		s += bit == '0' ? 0:1;
		count++;
		
		if(count == 8) {
			d.writeByte(s);
		
			count = 0;
			s = 0;
			return;
		}
		
	}

	public void close() throws IOException {
		// write final byte (if needed)
		// close the DataOutputStream
		d.close();

	}

	
}
