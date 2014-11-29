package com.example.sjy.cal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;


public class MyActivity extends Activity implements View.OnClickListener

{
    static boolean wantToMadd=false;
    static Float M;
    Button log_bt;
    private Button clear_bt;
    Button m_bt;
    Button madd_bt;
    Button mc_bt;
    Button ms_bt;
    Button switch_bt;
    Button equal2_bt;
    Button reciprocal_bt;
    Button sqrt_bt;
    Button sin_bt;
    Button div_bt;
    Button multi_bt;
    Button back_bt;
    Button seven_bt;
    Button eight_bt;
    Button nine_bt;
    Button sub_bt;
    Button four_bt;
    Button five_bt;
    Button six_bt;
    Button add_bt;
    Button one_bt;
    Button two_bt;
    Button three_bt;
    Button bracket_bt;
    Button zero_bt;
    Button point_bt;
    Button equal_bt;
    TextView tv;
    TextView result_tv;
    LinearLayout li1, li2;
    static StringBuilder stringBuilder;
    Calres calres = new Calres(this);
    static double ANS = 0;
    static int maxNumberCount = 15;
    int nowNumberCount = 0;
    int bracketNum = 0;
    boolean hasPoint = false;
    static boolean canAnsIn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        SharedPreferences setM = getPreferences(0);
        M = setM.getFloat("M", 0);
        init();
    }

    public void initFlag() {
        nowNumberCount = 0;
        hasPoint = false;
        result_tv.setTextColor(getResources().getColor(android.R.color.darker_gray));


    }

    @Override
    protected void onStop() {
        SharedPreferences setM = getPreferences(0);
        SharedPreferences.Editor editor = setM.edit();
        editor.putFloat("M", M);
        editor.commit();
        super.onStop();
    }

    public void init() {
        result_tv = (TextView) findViewById(R.id.result_tv);
        log_bt = (Button) findViewById(R.id.log_bt);
        log_bt.setOnClickListener(this);
        m_bt = (Button) findViewById(R.id.m_bt);
        m_bt.setOnClickListener(this);
        madd_bt = (Button) findViewById(R.id.madd_bt);
        madd_bt.setOnClickListener(this);
        mc_bt = (Button) findViewById(R.id.mc_bt);
        mc_bt.setOnClickListener(this);
//        ms_bt = (Button) findViewById(R.id.ms_bt);
//        ms_bt.setOnClickListener(this);
        switch_bt = (Button) findViewById(R.id.switch_bt);
        switch_bt.setOnClickListener(this);
        equal2_bt = (Button) findViewById(R.id.equal_bt2);
        equal2_bt.setOnClickListener(this);
        reciprocal_bt = (Button) findViewById(R.id.reciprocal_bt);
        reciprocal_bt.setOnClickListener(this);
        sqrt_bt = (Button) findViewById(R.id.sqrt_bt);
        sqrt_bt.setOnClickListener(this);
        sin_bt = (Button) findViewById(R.id.sin_bt);
        sin_bt.setOnClickListener(this);
        li1 = (LinearLayout) findViewById(R.id.li1);
        li2 = (LinearLayout) findViewById(R.id.li2);
        clear_bt = (Button) findViewById(R.id.clear_bt);
        clear_bt.setOnClickListener(this);
        div_bt = (Button) findViewById(R.id.divide_bt);
        div_bt.setOnClickListener(this);
        multi_bt = (Button) findViewById(R.id.multi_bt);
        multi_bt.setOnClickListener(this);
        back_bt = (Button) findViewById(R.id.backspace_bt);
        back_bt.setOnClickListener(this);
        seven_bt = (Button) findViewById(R.id.seven_bt);
        seven_bt.setOnClickListener(this);
        eight_bt = (Button) findViewById(R.id.eight_bt);
        eight_bt.setOnClickListener(this);
        nine_bt = (Button) findViewById(R.id.nine_bt);
        nine_bt.setOnClickListener(this);
        sub_bt = (Button) findViewById(R.id.sub_bt);
        sub_bt.setOnClickListener(this);
        four_bt = (Button) findViewById(R.id.four_bt);
        four_bt.setOnClickListener(this);
        five_bt = (Button) findViewById(R.id.five_bt);
        five_bt.setOnClickListener(this);
        six_bt = (Button) findViewById(R.id.six_bt);
        six_bt.setOnClickListener(this);
        add_bt = (Button) findViewById(R.id.add_bt);
        add_bt.setOnClickListener(this);
        one_bt = (Button) findViewById(R.id.one_bt);
        one_bt.setOnClickListener(this);
        two_bt = (Button) findViewById(R.id.two_bt);
        two_bt.setOnClickListener(this);
        three_bt = (Button) findViewById(R.id.three_bt);
        three_bt.setOnClickListener(this);
        bracket_bt = (Button) findViewById(R.id.bracket_bt);
        bracket_bt.setOnClickListener(this);
        zero_bt = (Button) findViewById(R.id.zero_bt);
        zero_bt.setOnClickListener(this);
        point_bt = (Button) findViewById(R.id.point_bt);
        point_bt.setOnClickListener(this);
        equal_bt = (Button) findViewById(R.id.equal_bt);
        equal_bt.setOnClickListener(this);
        tv = (TextView) findViewById(R.id.tv);
        stringBuilder = new StringBuilder();
        result_tv.setTextColor(getResources().getColor(android.R.color.darker_gray));
        back_bt.setLongClickable(true);
        back_bt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                hasPoint = false;
                nowNumberCount = 0;
                while (stringBuilder.length() != 0 && ((stringBuilder.charAt(stringBuilder.length() - 1) >= '0' && stringBuilder.charAt(stringBuilder.length() - 1) <= '9') || stringBuilder.charAt(stringBuilder.length() - 1) == '.')) {
                    stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
                }
                if (stringBuilder.length() != 0)
                    tv.setText(stringBuilder.toString());
                else tv.setText("0.0");
                return true;
            }
        });


    }

    public void errorOccur(String errorMessage) {
        result_tv.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        result_tv.setText(errorMessage);
        nowNumberCount = 0;
        hasPoint = false;
        bracketNum = 0;
        canAnsIn = false;
        wantToMadd=false;
        stringBuilder.delete(0, stringBuilder.length());
    }

    public void showAns(String result) {
        if (result.contains(".")) {
            result = result.replaceAll("0+?$", "");
            result = result.replaceAll("[.]$", "");
        }

        result_tv.setText(result);
        ANS = Double.parseDouble(result);
        canAnsIn = true;
        if (wantToMadd)
        {
            M+=Float.parseFloat(result);
            wantToMadd=false;
            initFlag();
            bracketNum = 0;
            ANS = 0;
            canAnsIn = false;
            result_tv.setText("M+ already done");
            stringBuilder.delete(0, stringBuilder.length());

        }
    }

    public boolean isOutMaxNum(int num) {
        if (num >= maxNumberCount) {
            Toast.makeText(this, "超出最大数位（15）", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public int nearestNumLastIndex(StringBuilder string) {
        if (string.charAt(string.length() - 1) == ')')
            return string.length() - 1;

        if (string.charAt(string.length() - 1) > '9' || string.charAt(string.length() - 1) < '0')
            return string.length() - 2;

        for (int i = string.length() - 1; i >= 0; i--) {
            if (string.charAt(i) >= '0' && string.charAt(i) <= '9')
                return i;
        }
        return -1;
    }

    public int nearestNumCount(StringBuilder string) {
        int temp = nearestNumLastIndex(string);
        hasPoint = false;
        if (temp != -1) {
            for (int i = temp - 1; i >= 0; i--) {
                if (string.charAt(i) < '0' || string.charAt(i) > '9') {
                    if (string.charAt(i) != '.')
                        return temp - i;
                    else hasPoint = true;
                }
            }
            return temp;

        }

        return -1;
    }

    public void singleFunc(String string) {
        if (stringBuilder.length() != 0)
            if (stringBuilder.charAt(stringBuilder.length() - 1) == '.') {
                hasPoint = false;
                stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "*" + string);
            } else if (stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                stringBuilder.append("*" + string);
                bracketNum++;
            } else if (stringBuilder.charAt(stringBuilder.length() - 1) >= '0' && stringBuilder.charAt(stringBuilder.length() - 1) <= '9') {
                stringBuilder.append("*" + string);
                bracketNum++;
            } else {
                stringBuilder.append(string);
                bracketNum++;
            }
        else {
            stringBuilder.append(string);
            bracketNum++;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mc_bt:
                this.M= Float.valueOf(0);
                if (stringBuilder.length()>0&&stringBuilder.charAt(stringBuilder.length()-1)=='M')
                    stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
                result_tv.setText("M=0");
                break;

            case R.id.m_bt:
                canAnsIn = false;
                if (stringBuilder.length() != 0 && (stringBuilder.charAt(stringBuilder.length() - 1) == ')'||(stringBuilder.charAt(stringBuilder.length()-1)>='0'&&stringBuilder.charAt(stringBuilder.length()-1)<='9'))) {
                    stringBuilder.append("*M");
                } else stringBuilder.append("M");

                break;
            case R.id.madd_bt:
                wantToMadd=true;
            case R.id.equal_bt:
            case R.id.equal_bt2:
                if (stringBuilder.length() != 0) {
                    calres.cal();
                }
                break;
            case R.id.reciprocal_bt:
                singleFunc("1/(");
                break;
            case R.id.log_bt:
                initFlag();
                singleFunc("log(");
                break;
            case R.id.sin_bt:
                initFlag();
                singleFunc("sin(");
                break;
            case R.id.sqrt_bt:
                initFlag();
                singleFunc("sqrt(");
                break;
            case R.id.switch_bt:
                if (li1.getVisibility() == View.GONE) {
                    li1.setVisibility(View.VISIBLE);
                    li2.setVisibility(View.GONE);
                } else {
                    li1.setVisibility(View.GONE);
                    li2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.backspace_bt:
                if (stringBuilder.length() >= 1) {
                    if ((stringBuilder.length() >= 2) && (stringBuilder.charAt(stringBuilder.length() - 1) > '9' || stringBuilder.charAt(stringBuilder.length() - 1) < '0') && stringBuilder.charAt(stringBuilder.length() - 2) == 'S') {
                        stringBuilder.delete(0, stringBuilder.length());
                        canAnsIn = true;
                        break;
                    }
                    if (stringBuilder.charAt(stringBuilder.length() - 1) == '(') {
                        bracketNum--;
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        while (stringBuilder.length() >= 1 && (stringBuilder.charAt(stringBuilder.length() - 1) >= 'a' && stringBuilder.charAt(stringBuilder.length() - 1) <= 'z')) {
                            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        }  //  sin( log( sqrt(的整体删除
                        break;
                    }
                    if (stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                        bracketNum++;
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                    if (stringBuilder.length() != 0) {
                        if ((stringBuilder.charAt(stringBuilder.length() - 1) >= '0' && stringBuilder.charAt(stringBuilder.length() - 1) <= '9') || stringBuilder.charAt(stringBuilder.length() - 1) == '.') {
                            nowNumberCount = nearestNumCount(stringBuilder) + 1;
                        } else {
                            initFlag();
                        }
                        if (stringBuilder.charAt(stringBuilder.length() - 1) == '.')
                            hasPoint = true;
                    }
                }
                break;
            case R.id.bracket_bt:
                initFlag();
                if (stringBuilder.length() != 0)
                    if (stringBuilder.charAt(stringBuilder.length() - 1) >= '0' && stringBuilder.charAt(stringBuilder.length() - 1) <= '9'||stringBuilder.charAt(stringBuilder.length()-1)=='M')
                        if (bracketNum == 0) {
                            stringBuilder.append("*(");
                            bracketNum++;
                        } else {
                            stringBuilder.append(")");
                            bracketNum--;
                        }
                    else if (stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                        if (bracketNum == 0) {
                            stringBuilder.append("*(");
                            bracketNum++;
                        } else {
                            stringBuilder.append(")");
                            bracketNum--;
                        }

                    } else {
                        stringBuilder.append("(");
                        bracketNum++;
                    }
                else {
                    stringBuilder.append("(");
                    bracketNum++;
                }
                break;
            case R.id.clear_bt:
                initFlag();
                bracketNum = 0;
                ANS = 0;
                canAnsIn = false;
                result_tv.setText("0.0");
                stringBuilder.delete(0, stringBuilder.length());
                break;
            case R.id.divide_bt:
                initFlag();
                if (canAnsIn) {
                    initFlag();
                    bracketNum = 0;
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append("ANS/");
                    canAnsIn = false;
                } else if (stringBuilder.length() != 0) {
                    if (stringBuilder.charAt(stringBuilder.length()-1)=='M')
                        stringBuilder.append('/');
                    else if (stringBuilder.charAt(stringBuilder.length() - 1) != '(')
                        stringBuilder.replace(nearestNumLastIndex(stringBuilder) + 1, stringBuilder.length(), "/");
                }
                break;
            case R.id.multi_bt:
                initFlag();
                if (canAnsIn) {
                    initFlag();
                    bracketNum = 0;
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append("ANS*");
                    canAnsIn = false;
                } else if (stringBuilder.length() != 0) {
                    if (stringBuilder.charAt(stringBuilder.length()-1)=='M')
                        stringBuilder.append('*');
                    else if (stringBuilder.charAt(stringBuilder.length() - 1) != '(')
                        stringBuilder.replace(nearestNumLastIndex(stringBuilder) + 1, stringBuilder.length(), "*");
                }
                break;
            case R.id.add_bt:
                initFlag();
                if (canAnsIn) {
                    initFlag();
                    bracketNum = 0;
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append("ANS+");
                    canAnsIn = false;
                } else if (stringBuilder.length() != 0) {
                    if (stringBuilder.charAt(stringBuilder.length()-1)=='M')
                        stringBuilder.append('+');
                    else if (stringBuilder.charAt(stringBuilder.length() - 1) != '(')
                        stringBuilder.replace(nearestNumLastIndex(stringBuilder) + 1, stringBuilder.length(), "+");

                }
                break;
            case R.id.sub_bt:
                initFlag();
                if (canAnsIn) {
                    initFlag();
                    bracketNum = 0;
                    stringBuilder.delete(0, stringBuilder.length());
                    stringBuilder.append("ANS-");
                    canAnsIn = false;
                } else if (stringBuilder.length() != 0) {
                    if (stringBuilder.charAt(stringBuilder.length()-1)=='M')
                        stringBuilder.append('-');
                    else if (stringBuilder.charAt(stringBuilder.length() - 1) != '(')
                        stringBuilder.replace(nearestNumLastIndex(stringBuilder) + 1, stringBuilder.length(), "-");
                    else {
                        stringBuilder.append("-");

                    }
                }
                break;
            case R.id.one_bt:
                canAnsIn = false;
                if (isOutMaxNum(nowNumberCount)) break;
                if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                    stringBuilder.append("*1");
                } else stringBuilder.append("1");
                nowNumberCount++;
                break;
            case R.id.two_bt:
                canAnsIn = false;
                if (isOutMaxNum(nowNumberCount)) break;
                if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                    stringBuilder.append("*2");
                } else stringBuilder.append("2");
                nowNumberCount++;
                break;
            case R.id.three_bt:
                canAnsIn = false;
                if (isOutMaxNum(nowNumberCount)) break;
                if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                    stringBuilder.append("*3");
                } else stringBuilder.append("3");
                nowNumberCount++;
                break;
            case R.id.four_bt:
                canAnsIn = false;
                if (isOutMaxNum(nowNumberCount)) break;
                if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                    stringBuilder.append("*4");
                } else stringBuilder.append("4");
                nowNumberCount++;
                break;
            case R.id.five_bt:
                canAnsIn = false;
                if (isOutMaxNum(nowNumberCount)) break;
                if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                    stringBuilder.append("*5");
                } else stringBuilder.append("5");
                nowNumberCount++;
                break;
            case R.id.six_bt:
                canAnsIn = false;
                if (isOutMaxNum(nowNumberCount)) break;
                if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                    stringBuilder.append("*6");
                } else stringBuilder.append("6");
                nowNumberCount++;
                break;
            case R.id.seven_bt:
                canAnsIn = false;
                if (isOutMaxNum(nowNumberCount)) break;
                if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                    stringBuilder.append("*7");
                } else stringBuilder.append("7");
                nowNumberCount++;
                break;
            case R.id.eight_bt:
                canAnsIn = false;
                if (isOutMaxNum(nowNumberCount)) break;
                if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                    stringBuilder.append("*8");
                } else stringBuilder.append("8");
                nowNumberCount++;
                break;
            case R.id.nine_bt:
                canAnsIn = false;
                if (isOutMaxNum(nowNumberCount)) break;
                if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                    stringBuilder.append("*9");
                } else stringBuilder.append("9");
                nowNumberCount++;
                break;
            case R.id.zero_bt:
                canAnsIn = false;
                if (isOutMaxNum(nowNumberCount)) break;
                if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                    stringBuilder.append("*0");
                } else stringBuilder.append("0");
                nowNumberCount++;
                break;
            case R.id.point_bt:
                canAnsIn = false;
                if (isOutMaxNum(nowNumberCount)) break;
                if (nowNumberCount == 0) {
                    if (stringBuilder.length() != 0 && stringBuilder.charAt(stringBuilder.length() - 1) == ')') {
                        stringBuilder.append("*0.");
                    } else stringBuilder.append("0.");

                    nowNumberCount += 2;
                    hasPoint = true;
                } else if (!hasPoint) {
                    stringBuilder.append(".");
                    hasPoint = true;
                    nowNumberCount++;
                } else {

                }
                break;
        }
        if (stringBuilder.length() != 0)
            tv.setText(stringBuilder.toString());
        else tv.setText("0.0");
    }
}
