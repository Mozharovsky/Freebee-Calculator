package com.main.java.calculator.Model;

import java.util.ArrayList;

/**
 * Created by E. Mozharovsky on 07.05.14.
 */
public class Body {
    private String str = null;
    private ArrayList<String> nums = new ArrayList<String>();
    private ArrayList<String> unknownMems = new ArrayList<String>();
    private ArrayList<Integer> knownMems = new ArrayList<Integer>();

    public ArrayList<Integer> getKnownMems() {
        return knownMems;
    }

    public ArrayList<String> getUnknownMems() {
        return unknownMems;
    }

    public Body(String str) {
        this.str = str;

        findSimpleNUmbers();
        findComplexNumbers();

        optimize();
    }

    private void findComplexNumbers() {
        String result = "";

        for(int i = 0; i < str.length(); i++) {
            if((i + 1) < str.length() && Character.isDigit(str.charAt(i)) && Character.isDigit(str.charAt(i + 1))) {
                result += Integer.parseInt(Character.toString(str.charAt(i)));
            } else if((i - 1) >= 0 && Character.isDigit(str.charAt(i)) && Character.isDigit(str.charAt(i - 1))) {
                result += Integer.parseInt(Character.toString(str.charAt(i)));

                if((i + 1) < str.length() && Character.isLetter(str.charAt(i + 1))) {
                    result += Character.toString(str.charAt(i + 1));
                }

                /**
                 * If you need to work with unknown mems, add code here !!!
                 */
            } else {
                if(result != "" && result != null) {
                    nums.add((result));
                }

                result = "";
            }

            if((i + 1) >= str.length()) {
                if(result != "" && result != null) {
                    nums.add((result));
                }
            }
        }

        for(String element : nums) {
            for(int i = 0; i < element.length(); i++) {
                if(Character.isLetter(element.charAt(i))) {
                    unknownMems.add(element);
                }
            }
        }

        nums.removeAll(unknownMems);

        for(String element : nums) {
            knownMems.add(Integer.parseInt(element));
        }

        for(String element : unknownMems) {
            if(isNegative(element)) {
                unknownMems.set(unknownMems.indexOf(element), ("-" + element));
            }
        }

        for(int digit : knownMems) {
            if(isNegative(Integer.toString(digit))) {
                knownMems.set(knownMems.indexOf(digit), (-1) * digit);
            }
        }

        nums.clear();
    }

    private void findSimpleNUmbers() {
        for(int i = 0; i < str.length(); i++) {
            if((i + 1) < str.length() && Character.isDigit(str.charAt(i)) && !Character.isDigit(str.charAt(i + 1))) {
                if((i - 1) >= 0 && !Character.isDigit(str.charAt(i - 1)) || i == 0) {
                    nums.add((Character.toString(str.charAt(i))));

                    if((i + 1) < str.length() && Character.isLetter(str.charAt(i + 1))) {
                        nums.set(nums.indexOf(Character.toString(str.charAt(i))), (Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1))));
                    }
                }
            } else if((i + 1) >= str.length() && (i - 1) >= 0 && str.charAt(i - 1) == ' ' && Character.isDigit(str.charAt(i))) {
                nums.add((Character.toString(str.charAt(i))));

                if((i + 1) < str.length() && Character.isLetter(str.charAt(i + 1))) {
                    nums.set(nums.indexOf(Character.toString(str.charAt(i))), (Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1))));
                }
            }
        }

        for(String element : nums) {
            for(int i = 0; i < element.length(); i++) {
                if(Character.isLetter(element.charAt(i))) {
                    unknownMems.add(element);
                }
            }
        }

        nums.removeAll(unknownMems);

        for(String element : unknownMems) {
            if(isNegative(element)) {
                unknownMems.set(unknownMems.indexOf(element), "-" + unknownMems.get(unknownMems.indexOf(element)));
            }
        }

        for(String element : nums) {
            knownMems.add(Integer.parseInt(element));
        }

        for(int digit : knownMems) {
            if(isNegative(Integer.toString(digit))) {
                knownMems.set(knownMems.indexOf(digit), (-1) * knownMems.get(knownMems.indexOf(digit)));
            }
        }

        nums.clear();
    }

    private boolean isKnownMems(String region) {
        for(int i = 0; i < region.length(); i++) {
            if(Character.isLetter(region.charAt(i))) {
                return false; // i.e. unknown mems
            }
        }

        return true;
    }

    private int iterationsCountOnRegion(String region) {
        int result = 0;
        if(isKnownMems(region)) {
            for(int i = 0; i < knownMems.size(); i++) {
                for(int j = 0; j < knownMems.size(); j++) {
                    if(knownMems.get(i) == knownMems.get(j) && knownMems.get(i) == Integer.parseInt(region)) {
                        result++;
                    }
                }
            }
        } else {
            for(int i = 0; i < unknownMems.size(); i++) {
                for(int j = 0; j < unknownMems.size(); j++) {
                    if(unknownMems.get(i).equals(unknownMems.get(j)) && unknownMems.get(i).equals(region)) {
                        result++;
                    }
                }
            }
        }

        return result;
    }

    private boolean isNegative(String region) {
        boolean status = false;

        for(int i = str.indexOf(region); i >= 0; i--) {
            if(i < str.length() && str.charAt(i) == '-') {
                status =  true;
                break;
            } else if(i < str.length() && str.charAt(i) == '+') {
                status = false;
                break;
            }
        }

        if(iterationsCountOnRegion(region) > 0) {
            str = str.replaceFirst(region, "");
        }

        return status;
    }

    public void optimize() {
        /**
         * Digits
         */

        if(!knownMems.isEmpty()) {
            int result = knownMems.get(0);
            for(int i = 0; i < knownMems.size(); i++) {
                if((i + 1) < knownMems.size()) {
                    result += knownMems.get(i + 1);
                }
            }

            knownMems.clear();
            knownMems.add(result);
        }

        /**
         * Unknowns
         */

        if(!unknownMems.isEmpty()) {
            boolean status = true;
            String commonChar = "";
            String tmp = "";

            for(String element : unknownMems) {
                for(int i = 0; i < element.length(); i++) {
                    if(Character.isLetter(element.charAt(i))) {
                        tmp = commonChar;
                        commonChar = Character.toString(element.charAt(i));

                        if(!tmp.equals("") && !tmp.equals(commonChar)) {
                            status = false;
                        }
                    }
                }
            }

            if(status) {
                for(int i = 0; i < unknownMems.size(); i++) {
                    unknownMems.set(i, unknownMems.get(i).replace(commonChar, ""));
                }

                int result = Integer.parseInt(unknownMems.get(0));
                for(int i = 0; i < unknownMems.size(); i++) {
                    if((i + 1) < unknownMems.size()) {
                        result += Integer.parseInt(unknownMems.get(i + 1));
                    }
                }

                unknownMems.clear();
                unknownMems.add(Integer.toString(result) + commonChar);
            } else {
                System.out.println("Failed to load unknown mems ...");
            }
        }
    }
}
