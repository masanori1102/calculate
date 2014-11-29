package com.example.sjy.cal;

import android.util.Log;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by sjy on 14-11-28.
 */
public class Calres {
    static final double TargetAccurary = 0.00000001;
    Boolean isError = false;
    static Map<String, Double> priMap = new HashMap<String, Double>();
    MyActivity activity;
    static StringBuilder stringBuilder;
    String[] infixSplit;
    Stack<String> funcStack = new Stack<String>();
    List<String> suffixList = new ArrayList<String>();
    Stack<BigDecimal> decimalStack = new Stack<BigDecimal>();

    public Calres(MyActivity activity) {
        this.activity = activity;
        priMap.put("(", 16.0);
        priMap.put("-", 2.4);
        priMap.put("+", 2.4);
        priMap.put("*", 2.2);
        priMap.put("/", 2.2);
        priMap.put("log", 1.0);
        priMap.put("sin", 1.0);
        priMap.put("sqrt", 1.0);


    }

    public void cal() {
        stringBuilder = new StringBuilder(activity.stringBuilder.toString());

        dealMinus();  //处理负号
        splitString(); //字符和数字分开
        generateSuffixList();  //生成后缀表达式
        countRes(); //利用后缀表达式计算结果
    }

    private void countRes() {

        try {
            decimalStack.removeAll(decimalStack);

            for (String i : suffixList) {
                isError = false;
                if (isNumorPoint(i.charAt(0)) || (suffixList.indexOf(i) == 0 && i.charAt(0) == '-')||(i.length()>=2&&i.charAt(0)=='-'&&isNumorPoint(i.charAt(1))))
                    decimalStack.push(new BigDecimal(i));
                else if (i.equals("+")) {
                    decimalStack.push(dealwithDouble(decimalStack.pop().add(decimalStack.pop())));
                } else if (i.equals("-")) {
                    decimalStack.push(dealwithDouble(decimalStack.pop().subtract(decimalStack.pop()).negate()));
                } else if (i.equals("*")) {
                    decimalStack.push(dealwithDouble(decimalStack.pop().multiply(decimalStack.pop())));
                } else if (i.equals("/")) {
                    BigDecimal temp = decimalStack.pop();
                    if (temp.setScale(10, RoundingMode.HALF_UP).doubleValue() - 0 <= TargetAccurary) {
                        isError = true;
                        activity.errorOccur("be divided by 0");
                        break;
                    }
                    temp = decimalStack.pop().divide(temp, 10, BigDecimal.ROUND_HALF_UP);
                    decimalStack.push(dealwithDouble(temp));
                } else if (i.equals("sin")) {
                    decimalStack.push(dealwithDouble(new BigDecimal(Math.sin(Math.toRadians(decimalStack.pop().doubleValue())))));
                } else if (i.equals("sqrt")) {
                    BigDecimal temp = decimalStack.pop();
                    if (temp.doubleValue() < 0) {
                        isError = true;
                        activity.errorOccur("negative in sqrt ");
                        break;
                    }
                    decimalStack.push(dealwithDouble(new BigDecimal(Math.sqrt(temp.doubleValue()))));
                } else if (i.equals("log")) {
                    BigDecimal temp = decimalStack.pop();
                    if (temp.doubleValue() < 0) {
                        isError = true;
                        activity.errorOccur("negative in log ");
                        break;
                    }
                    decimalStack.push(dealwithDouble(new BigDecimal(Math.log(temp.doubleValue()))));
                }


            }
            if (!isError) {
                activity.showAns(decimalStack.pop().toString());
            }
        } catch (Exception e) {
            activity.errorOccur("calculate error");
            activity.canAnsIn = false;
            activity.wantToMadd= false;
        }
    }

    public BigDecimal dealwithDouble(BigDecimal big) {
        if (Math.abs(big.doubleValue() - big.setScale(0, RoundingMode.CEILING).intValue()) <= TargetAccurary)
            return big.setScale(0, RoundingMode.CEILING);
        else if (Math.abs(big.doubleValue() - big.setScale(0, RoundingMode.FLOOR).intValue()) <= TargetAccurary)
            return big.setScale(0, RoundingMode.FLOOR);
        return big;
    }

    public void generateSuffixList() {
        try {


            suffixList.removeAll(suffixList);
            funcStack.removeAll(funcStack);


            for (String i : infixSplit) {
                if (i.equals("ANS")) {
                    suffixList.add(String.valueOf(activity.ANS));
                } else if (i.equals("M")) {
                    suffixList.add(String.valueOf(activity.M));
                } else if (isNumorPoint(i.charAt(0))) {
                    // Log.v("pp",i);
                    suffixList.add(i);
                } else if (funcStack.isEmpty()) {
                    //  Log.v("pp",i);
                    funcStack.push(i);
                } else if (i.charAt(0) == '(') {
                    funcStack.push(i);
                } else if (i.charAt(0) == ')') {
                    while (true) {
                        String temp = funcStack.pop();
                        if (temp.charAt(0) == '(')
                            break;
                        suffixList.add(temp);
                    }
                } else {
                    while (!funcStack.isEmpty() && !comparePRI(funcStack.peek(), i)) {
                        suffixList.add(funcStack.pop());
                    }
                    funcStack.push(i);

                }


            }
            while (!funcStack.isEmpty()) {
                suffixList.add(funcStack.pop());
            }
            for (String i : suffixList)
                Log.v("pp", i);
        } catch (Exception e) {
            activity.errorOccur("syntax error");
            activity.canAnsIn = false;
            activity.wantToMadd= false;
        }
    }

    public boolean comparePRI(String one, String two) {  //比较优先级
        if (Math.abs(priMap.get(one) - priMap.get(two)) < 0.0000000001) {
            return false;
        } else if (priMap.get(one) > priMap.get(two)) {
            return true;
        } else
            return false;


    }

    public void dealMinus() {
        for (int i = 0; i < stringBuilder.length() - 1; i++) {
            if (stringBuilder.charAt(i) == '(' && stringBuilder.charAt(i + 1) == '-') {
                stringBuilder.insert(i + 1, '0');
            }

        }
        Debug();
    }

    public void splitString() {
        for (int i = 0; i < stringBuilder.length() - 1; i++) {
            if (isNumorPoint(stringBuilder.charAt(i)) && !isNumorPoint(stringBuilder.charAt(i + 1)))
                stringBuilder.insert(i + 1, ' ');

            else if (isTargerChar(stringBuilder.charAt(i)) && isNumorPoint(stringBuilder.charAt(i + 1)))
                stringBuilder.insert(i + 1, ' ');
            else if (isTargerChar(stringBuilder.charAt(i)) && isTargerChar(stringBuilder.charAt(i + 1)))
                stringBuilder.insert(i + 1, ' ');
        }

        infixSplit = stringBuilder.toString().split(" ");

    }

    public boolean isTargerChar(char c) {
        if (c == '/' || c == '*' || c == '+' || c == '-' || c == '(' || c == ')' || c == 's' || c == 'n' || c == 'l' || c == 'g' || c == 't' || c == 'S' || c == 'M')
            return true;
        return false;
    }

    public boolean isNumorPoint(char c) {
        if ((c >= '0' && c <= '9') || c == '.')
            return true;
        return false;
    }

    private void Debug() {
        Log.v("pp", stringBuilder.toString());
    }
}
