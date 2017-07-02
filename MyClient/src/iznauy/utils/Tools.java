package iznauy.utils;

/**
 * 工具类，用于美化BF文件
 * @author iznauy
 *
 */
public abstract class Tools {
	
	public static String processBFText(String original) {
		StringBuffer buffer;
		String tab = "    ";
		int enterCount = 0;
		char[] rawChars = original.toCharArray();
        StringBuffer programBuilder = new StringBuffer();
        for (char i : rawChars) {
            if (i == '+' || i == '-'
                    || i == '>'
                    || i == '<'
                    || i == ','
                    || i == '.'
                    || i == '['
                    || i == ']') {
            	if (i == '[') {
            		if (programBuilder.length() != 0) {
            			enterCount++;
            			if (!Character.isSpaceChar(programBuilder.toString().charAt(programBuilder.length() - 1))) {
            				programBuilder.append(System.lineSeparator());
            			}
                		buffer = new StringBuffer("");
                		for (int j = 0; j < enterCount; j++) {
                			buffer.append(tab);
                		}
                		programBuilder.append(buffer.toString());
                		programBuilder.append(i);
                		programBuilder.append(System.lineSeparator());
                		programBuilder.append(buffer.append(tab).toString());
                		enterCount++;
            		} else {
            			programBuilder.append(i);
            		}
            	} else if (i == ']') {
            		programBuilder.append(System.lineSeparator());
            		buffer = new StringBuffer("");
            		enterCount--;
            		for (int j = 0; j < enterCount - 1; j++) {
            			buffer.append(tab);
            		}
            		//if (buffer.length() != 0) {
            			programBuilder.append(buffer.toString() + tab);
            		//}
            		programBuilder.append(i);
            		programBuilder.append(System.lineSeparator());
            		programBuilder.append(buffer.toString());
            		enterCount--;
            	} else {
            		programBuilder.append(i);
            	}
            }
        }
        return programBuilder.toString();
	}
	
	public static String processOokText(String original) {
		char[] rawChars = original.toCharArray();
        StringBuffer programBuilder = new StringBuffer();
        for (char i : rawChars) {
            if (i == 'O' || i == 'o'
                    || i == '.'
                    || i == '!'
                    || i == '?'
                    || i == 'k') {
                programBuilder.append(i);
            }
        }
        return programBuilder.toString();
	}
	
	

}
