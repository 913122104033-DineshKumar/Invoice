package invoice.utils;

import java.time.LocalDate;

public class ComparisonUtil
{
    // Comparisons
    public static int compareIntegers(int value1, int value2)
    {

        return value1 <= value2 ? 1 : -1;

    }

    public static int compareDoubles (double value1, double value2)
    {
        return value1 <= value2 ? 1 : -1;
    }

    public static int compareStrings (String s1, String s2)
    {

        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int s1Index = 0, s2Index = 0;

        while (s1Index < s1.length() && s2Index < s2.length())
        {
            if (s1.charAt(s1Index) < s2.charAt(s2Index))
            {
                return 1;
            }
            else if (s1.charAt(s1Index) > s2.charAt(s2Index))
            {
                return -1;
            } else
            {
                s1Index++;
                s2Index++;
            }
        }

        if (s1Index == s1.length()) {
            return 1;
        }

        return -1;
    }

    public static int compareDates (LocalDate date1, LocalDate date2)
    {

        if (date1.getYear() < date2.getYear())
        {
            return 1;
        }
        else if (date1.getYear() > date2.getYear())
        {
            return -1;
        } else
        {
            if (date1.getMonthValue() < date2.getMonthValue())
            {
                return 1;
            }
            else if (date1.getMonthValue() > date2.getMonthValue())
            {
                return -1;
            } else
            {
                if (date1.getDayOfMonth() <= date2.getDayOfMonth())
                {
                    return 1;
                } else
                {
                    return -1;
                }
            }
        }
    }
}
