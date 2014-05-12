package com.main.java.calculator.Model;

import java.util.ArrayList;

/**
 * Created by E. Mozharovsky on 07.05.14.
 */
public class Body {
    private String str = null;
    private ArrayList<String> nums = new ArrayList<String>();
    private ArrayList<String> quadraticMems = new ArrayList<String>();
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
        int spe_start = -1; // The index of '=' mark
        boolean isTouched = false;
        String result = "";

        for(int i = 0; i < str.length(); i++) {
            if((i + 1) < str.length() && Character.isDigit(str.charAt(i)) && Character.isDigit(str.charAt(i + 1))) {
                result += Integer.parseInt(Character.toString(str.charAt(i)));
                isTouched = true;
            } else if((i - 1) >= 0 && Character.isDigit(str.charAt(i)) && Character.isDigit(str.charAt(i - 1))) {
                result += Integer.parseInt(Character.toString(str.charAt(i)));

                if((i + 1) < str.length() && Character.isLetter(str.charAt(i + 1))) {
                    result += Character.toString(str.charAt(i + 1));

                    if((i + 3) < str.length() && str.charAt(i + 2) == '^' && str.charAt(i + 3) == '2') {
                        result += Character.toString(str.charAt(i + 2)) + Character.toString(str.charAt(i + 3));
                    }
                }

                if(i > spe_start && spe_start != -1) {
                    result = "-" + result;
                }

                isTouched = true;
            } else {
                // Check for the second expression part (after '=' mark)
                if(str.charAt(i) == '=') {
                    spe_start = i;
                }

                if(result != "^2" && result != "" && result != null) {
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

        if(isTouched) {
            for(int i = 0; i < unknownMems.size(); i++) {
                if(isNegative(unknownMems.get(i))) {
                    unknownMems.set(i, ("-" + unknownMems.get(i)));
                }
            }

            for(int i = 0; i < knownMems.size(); i++) {
                if(isNegative(Integer.toString(knownMems.get(i)))) {
                    knownMems.set(i, (-1) * knownMems.get(i));
                }
            }

            nums.clear();
        }
    }

    private void findSimpleNUmbers() {
        int spe_start = -1; // The index of '=' mark
        boolean isTouched = false;

        for(int i = 0; i < str.length(); i++) {
            if((i + 1) < str.length() && Character.isDigit(str.charAt(i)) && !Character.isDigit(str.charAt(i + 1))) {
                if((i - 1) >= 0 && !Character.isDigit(str.charAt(i - 1)) && str.charAt(i - 1) != '^' || i == 0) {
                    nums.add((Character.toString(str.charAt(i))));

                    if((i + 2) < str.length() && Character.isLetter(str.charAt(i + 1)) && str.charAt(i + 2) != '^' || (i + 2) >= str.length() && Character.isLetter(str.charAt(i + 1))) {
                        nums.set(nums.indexOf(Character.toString(str.charAt(i))), (Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1))));

                        if(i > spe_start && spe_start != -1) {
                            nums.set(nums.indexOf((Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1)))), ("-" + nums.get(nums.indexOf((Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1)))))));
                        }
                    } else if((i + 3) < str.length() && str.charAt(i + 2) == '^' && str.charAt(i + 3) == '2') {
                        nums.set(nums.indexOf(Character.toString(str.charAt(i))), (Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1)) + Character.toString(str.charAt(i + 2)) + Character.toString(str.charAt(i + 3))));

                        if(i > spe_start && spe_start != -1) {
                            nums.set(nums.indexOf((Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1)) + Character.toString(str.charAt(i + 2)) + Character.toString(str.charAt(i + 3)))), ("-" + nums.get(nums.indexOf((Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1)) + Character.toString(str.charAt(i + 2)) + Character.toString(str.charAt(i + 3)))))));
                        }

                        System.out.println(nums);
                    } else {
                        if(i > spe_start && spe_start != -1) {
                            nums.set(nums.indexOf((Character.toString(str.charAt(i)))), ("-" + nums.get(nums.indexOf((Character.toString(str.charAt(i)))))));
                        }
                    }

                    isTouched = true;
                }
            } else if((i + 1) >= str.length() && (i - 1) >= 0 && str.charAt(i - 1) == ' ' && Character.isDigit(str.charAt(i))) {
                if((i + 2) < str.length() && Character.isLetter(str.charAt(i + 1)) && (i + 2) >= str.length()) {
                    nums.set(nums.indexOf(Character.toString(str.charAt(i))), (Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1))));

                    if(i > spe_start && spe_start != -1) {
                        nums.set(nums.indexOf((Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1)))), ("-" + nums.get(nums.indexOf((Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1)))))));
                    }
                } else if((i + 3) < str.length() && str.charAt(i + 2) == '^' && str.charAt(i + 3) == '2') {
                    nums.set(nums.indexOf(Character.toString(str.charAt(i))), (Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1)) + Character.toString(str.charAt(i + 2)) + Character.toString(str.charAt(i + 3))));

                    if(i > spe_start && spe_start != -1) {
                        nums.set(nums.indexOf((Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1)) + Character.toString(str.charAt(i + 2)) + Character.toString(str.charAt(i + 3)))), ("-" + nums.get(nums.indexOf((Character.toString(str.charAt(i)) + Character.toString(str.charAt(i + 1)) + Character.toString(str.charAt(i + 2)) + Character.toString(str.charAt(i + 3)))))));
                    }
                }
                /**
                 * May be BUGGY !!!
                 */
                else {
                    nums.add(Character.toString(str.charAt(i)));

                    if(i > spe_start && spe_start != -1) {
                        nums.set(nums.indexOf((Character.toString(str.charAt(i)))), ("-" + nums.get(nums.indexOf((Character.toString(str.charAt(i)))))));
                    }
                }

                isTouched = true;
            } else {
                if(str.charAt(i) == '=') {
                    spe_start = i;
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

        if(isTouched) {
            for(String element : unknownMems) {
                if(isNegative(element)) {
                    unknownMems.set(unknownMems.indexOf(element), "-" + unknownMems.get(unknownMems.indexOf(element)));
                }
            }


            for(int digit : knownMems) {
                if(isNegative(Integer.toString(digit))) {
                    knownMems.set(knownMems.indexOf(digit), (-1) * knownMems.get(knownMems.indexOf(digit)));
                }
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

        if(iterationsCountOnRegion(region) > 1) {
            region = region.replace("^2", "");
            str = str.replaceFirst(region, "");
        }

        return status;
    }

    /**
     * A prototype
     */
    private void optimize() {
        optimizeKnownMems();
        optimizeUnknownMems();
    }

    private void optimizeKnownMems() {
        if(!knownMems.isEmpty() && knownMems.size() > 1) {
            int result = knownMems.get(0);
            for(int i = 0; i < knownMems.size(); i++) {
                if((i + 1) < knownMems.size()) {
                    result += knownMems.get(i + 1);
                }
            }

            knownMems.clear();
            knownMems.add(result);
        }
    }

    private void optimizeUnknownMems() {
        if(!unknownMems.isEmpty() && unknownMems.size() > 1) {
            boolean status = true;
            String commonChar = "";
            String tmp = "";

            for(String element : unknownMems) {
                for(int i = 0; i < element.length(); i++) {
                   if((i + 2) < element.length() && Character.isLetter(element.charAt(i)) && element.charAt(i + 1) == '^' && element.charAt(i + 2) == '2') { /** a standard quadratic form */
                        tmp = commonChar;
                        commonChar = Character.toString(element.charAt(i));
                    } else if(Character.isLetter(element.charAt(i))) {
                        tmp = commonChar;
                        commonChar = Character.toString(element.charAt(i));

                        if(!tmp.equals("") && !tmp.equals(commonChar)) {
                            status = false;
                        }
                    }
                }
            }

            // FIXME: unknown optimizing
            if(status) {
                for(int i = 0; i < unknownMems.size(); i++) {
                    for(int j = 0; j < unknownMems.get(i).length(); j++) {
                        if((j + 2) < unknownMems.get(i).length()  && unknownMems.get(i).charAt(j + 1) == '^' && unknownMems.get(i).charAt(j + 2) == '2') { /** a standard quadratic form */
                            unknownMems.set(i, unknownMems.get(i).replace(commonChar + "^2", ""));
                            quadraticMems.add(unknownMems.get(i));
                        } else if((j + 1) >= unknownMems.get(i).length() && Character.isLetter(unknownMems.get(i).charAt(j)) && Character.isDigit(unknownMems.get(i).charAt(j - 1))) {
                            unknownMems.set(i, unknownMems.get(i).replace(commonChar, ""));
                        }
                    }
                }

                // To remove all iterated elements
                prepareUnknownMems();

                optimizeQuadraticMems(commonChar);

                if(unknownMems.size() > 0) {
                    // Unknown mems optimization
                    int result = Integer.parseInt(unknownMems.get(0));
                    for(int i = 0; i < unknownMems.size(); i++) {
                        if((i + 1) < unknownMems.size()) {
                            result += Integer.parseInt(unknownMems.get(i + 1));
                        }
                    }

                    unknownMems.clear();
                    unknownMems.add(Integer.toString(result) + commonChar);
                }

                unknownMems.addAll(quadraticMems);
                quadraticMems.clear();
            } else {
                System.out.println("Failed to load unknown mems ...");
            }
        }
    }

    private void optimizeQuadraticMems(String commonChar) {
        if(!quadraticMems.isEmpty() && quadraticMems.size() > 1) {
            int result = Integer.parseInt(quadraticMems.get(0));
            for(int i = 0; i < quadraticMems.size(); i++) {
                if((i + 1) < quadraticMems.size()) {
                    result += Integer.parseInt(quadraticMems.get(i + 1));
                }
            }

            quadraticMems.clear();
            quadraticMems.add(Integer.toString(result) + commonChar + "^2");
        } else if(!quadraticMems.isEmpty()) {
            quadraticMems.set(0, quadraticMems.get(0) + commonChar + "^2");
        }
    }

    private void prepareUnknownMems() {
        ArrayList<String> copy = new ArrayList<String>();
        ArrayList<Integer> indexesToRemove = new ArrayList<Integer>();

        copy.addAll(quadraticMems);

        for(int i = 0; i < unknownMems.size(); i++) {
            for(int j = 0; j < copy.size(); j++) {
                if(unknownMems.get(i).equals(copy.get(j))) {
                    indexesToRemove.add(i);
                }
            }
        }

        for(int i = (indexesToRemove.size() - 1); i >= 0; i--) {
            if(indexesToRemove.get(i) < unknownMems.size()) {
                unknownMems.remove(unknownMems.get(indexesToRemove.get(i)));
            }
        }

        copy = null;
        indexesToRemove = null;
    }
}
