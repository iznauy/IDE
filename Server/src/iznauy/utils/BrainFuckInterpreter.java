package iznauy.utils;

import iznauy.exception.LanStackOverException;
import iznauy.exception.StackOutBoundException;
import iznauy.exception.SyntaxError;

import java.util.HashMap;
import java.util.Stack;
import java.util.ArrayList;
/**
 * Created by iznauy on 2017/6/7.
 */
public class BrainFuckInterpreter implements Interpreter {

    public String executeProgram(String rawProgram, String input, int stepCount) {
        if (rawProgram == null || rawProgram.equals("")) {
            return "";
        }
        StringBuffer output = new StringBuffer();

        System.out.println("In executing!");

        try {
            int count = 0;
            int maxCount = 10000000;
            String program = filter(rawProgram);
            HashMap<Integer, Integer> jumpMap = precomputeJumps(program);
            ArrayList<Integer> buffer = new ArrayList<>();
            buffer.add(0);

            int ptr = 0;
            int pc = 0;
            int inputPtr = 0;

            while (pc != program.length()) {
                char opcode = program.charAt(pc);

                if (opcode == '>') {
                    ptr += 1;
                    if (ptr == buffer.size()) {
                        buffer.add(0);
                    }
                } else if (opcode == '<') {
                    ptr -= 1;
                    if (ptr < 0) {
                        throw new StackOutBoundException(StackOutBoundException.OUT_BOUDN);
                    }
                } else if (opcode == ',') {
                    if (input != null) {
                        buffer.set(ptr, Integer.valueOf(input.charAt(inputPtr)));
                    }
                    inputPtr++;
                } else if (opcode == '.') {
                    output.append(Character.toChars(buffer.get(ptr)));
                } else if (opcode == '[') {
                    if (buffer.get(ptr) == 0) {
                        pc = jumpMap.get(pc);
                    }
                } else if (opcode == ']') {
                    if (buffer.get(ptr) != 0) {
                        pc = jumpMap.get(pc);
                    }
                } else if (opcode == '+') {
                    buffer.set(ptr, buffer.get(ptr) + 1);
                } else if (opcode == '-') {
                    buffer.set(ptr, buffer.get(ptr) - 1);
                }
                pc += 1;
                count++;
                if (count == stepCount) {
                    return output.toString();
                }
                if (count > maxCount) {
                    throw new LanStackOverException();
                }
            }
        } catch(SyntaxError e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (StackOutBoundException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return "输入堵塞\n" + output.toString();
        } catch (LanStackOverException e) {
            e.printStackTrace();
            return "指令过多！" + output.toString();
        }
        return output.toString();
    }

    @Override
    public String executeProgram(String rawProgram, String input) {
        return executeProgram(rawProgram, input, 10000000);
    }

    private static String filter(String rawProgram) {
        char[] rawChars = rawProgram.toCharArray();
        StringBuffer programBuilder = new StringBuffer();
        for (char i : rawChars) {
            if (i == '+' || i == '-'
                    || i == '>'
                    || i == '<'
                    || i == ','
                    || i == '.'
                    || i == '['
                    || i == ']') {
                programBuilder.append(i);
            }
        }
        return programBuilder.toString();
    }

    private static HashMap<Integer, Integer> precomputeJumps(String program) throws SyntaxError {
        Stack<Integer> stack = new Stack<>();
        HashMap<Integer, Integer> ret = new HashMap<>();
        int pc = 0;

        while (pc != program.length()) {
            char opcode = program.charAt(pc);
            if (opcode == '[') {
                stack.push(pc);
            } else if (opcode == ']') {
                if(stack.isEmpty()) {
                    throw new SyntaxError(SyntaxError.UNEXPECTED_BACK_FRAME);
                }
                int target = stack.pop();
                ret.put(target, pc);
                ret.put(pc, target);
            }
            pc += 1;
        }
        if (!stack.isEmpty()) {
            throw new SyntaxError(SyntaxError.UNBLOCKED_FRONT_FRAME);
        }
        return ret;
    }
}
