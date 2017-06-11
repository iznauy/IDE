package iznauy.utils;

import iznauy.exception.SyntaxError;

/**
 * Created by iznauy on 2017/6/7.
 */
public class OokInterpreter implements Interpreter {

    @Override
    public String executeProgram(String rawProgram, String input) {
        String program;
        try {
            program = convertToBrainFuck(rawProgram);
        } catch (SyntaxError syntaxError) {
            syntaxError.printStackTrace();
            return syntaxError.getMessage();
        }
        return new BrainFuckInterpreter().executeProgram(program, input);
    }

    private static String filter(String rawProgram) {
        char[] rawChars = rawProgram.toCharArray();
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

    private static String convertToBrainFuck(String rawProgram) throws SyntaxError{
        String program = filter(rawProgram);
        int count = program.length() / 8;
        StringBuffer bfProgram = new StringBuffer();

        if (program.length() % 8 != 0) { //最好抛出一个异常，等待完善
            throw new SyntaxError(SyntaxError.INVALID_NAME);
        }
        for (int i = 0; i < count; i++) {
            String temp = program.substring(i * 8, i * 8 + 8);
            if (temp.equals("Ook.Ook?")) {
                bfProgram.append('>');
            } else if (temp.equals("Ook?Ook.")) {
                bfProgram.append('<');
            } else if (temp.equals("Ook.Ook.")) {
                bfProgram.append('+');
            } else if (temp.equals("Ook!Ook!")) {
                bfProgram.append('-');
            } else if (temp.equals("Ook!Ook.")) {
                bfProgram.append('.');
            } else if (temp.equals("Ook.Ook!")) {
                bfProgram.append(',');
            } else if (temp.equals("Ook!Ook?")) {
                bfProgram.append('[');
            } else if (temp.equals("Ook?Ook!")) {
                bfProgram.append(']');
            } else { //输入不合法
                throw new SyntaxError(SyntaxError.INVALID_NAME);
            }
        }
        return bfProgram.toString();
    }

}
