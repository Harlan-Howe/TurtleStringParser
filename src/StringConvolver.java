public class StringConvolver
{
    private String sourceString, replace1, with1;

    public void setSourceString(String sourceString)
    {
        this.sourceString = sourceString;
    }

    public void setReplace1(String replace1)
    {
        this.replace1 = replace1;
    }

    public void setWith1(String with1)
    {
        this.with1 = with1;
    }

    /**
     * replaces all occurances of the "replace1" string found in "sourceString" with "with1" string. Do this N times.
     *
     * Level 1: one character --> one character
     * For example, if sourceString is "bottom", replace1 is "o" and with1 is "i", then
     * convolveStringNTimes(0) --> "bottom"
     * convolveStringNTimes(1) --> "bittim"
     *
     * Level 2: one character --> multi characters
     * If sourceString is "I was washing my watermellon", replace1 is "w" and with1 is "two", then
     * convolveStringNTimes(0) --> "I was washing my watermellon"
     * convolveStringNTimes(1) --> "I twoas twoashing my twoatermellon"
     * convolveStringNTimes(2) --> "I ttwooas ttwooashing my ttwooatermellon"
     *
     * Level 3: multi character --> multi characters
     * If sourceString is "plant", replace1 is "an" and with1 is "handyman"
     * convolveStringNTimes(1) --> "plhandymant"
     * convolveStringNTimes(2) --> "plhhandymandymhandymant"
     * convolveStringNTimes(3) --> "plhhhandymandymhandymandymhhandymandymhandymant"
     *
     * Level 4: multiple replace-with pairs
     * If sourceString is "happy", replace1 is "ha" and with1 is "sna", and replace2 is "py" and with2 is "per"
     * convolveStringNTimes(0) --> "happy"
     * convolveStringNTimes(1) --> "snapper"
     * @param N - the number of times you should loop through this string doing a replacement pass
     * @return - a string generated from the source string with the replace-with cycle completed N times.
     */
    public String convolveStringNTimes(int N)
    {
        StringBuilder builder = new StringBuilder(sourceString);

        int replaceLength = replace1.length();
        int withLength = with1.length();
        for (int i=0; i<N; i++)
        {
            String tempSource = builder.toString();
            int tempSourceLength = tempSource.length();
            builder = new StringBuilder();
            for (int j=0; j<tempSourceLength; j++)
            {
                if (j+replaceLength<tempSourceLength+1)
                {
                    boolean matched = true;
                    for (int k=0; k<replaceLength; k++)
                        if (tempSource.charAt(j+k) != replace1.charAt(k))
                        {
                            matched = false;
                            break;
                        }
                    if (matched)
                    {
                        builder.append(with1);
                        j += (replaceLength - 1);
                    }
                    else
                        builder.append(tempSource.charAt(j));
                }
                else
                    builder.append(tempSource.charAt(j));
            }

        }

        return builder.toString(); // temporary code. Replace this. Otherwise, this behaves like it is always N=0.
    }



}
