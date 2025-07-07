package invoice.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SortingUtil
{

    public SortingUtil () { }

    public <T, U> void mergeSort (int low, int high, List<T> sourceList, List<U> helperList)
    {
        if (low >= high)
        {
            return;
        }

        int mid = (low + high) / 2;

        mergeSort(low, mid, sourceList, helperList);
        mergeSort(mid + 1, high, sourceList, helperList);
        merge(low, mid, high, sourceList, helperList);
    }

    private <T, U> void merge (int low, int mid, int high, List<T> sourceList, List<U> helperList)
    {
        List<T> leftSubList = fillSubList(low, mid, sourceList);
        List<U> leftHelperList = fillHelperList(low, mid, helperList);

        List<T> rightSubList = fillSubList(mid + 1, high, sourceList);
        List<U> rightHelperList = fillHelperList(mid + 1, high, helperList);

        int leftIndex = 0, rightIndex = 0;
        int currentIndex = low;

        while (leftIndex < leftSubList.size() && rightIndex < rightSubList.size())
        {
            int comparisonValue;

            U leftElement = leftHelperList.get(leftIndex);
            U rightElement = rightHelperList.get(rightIndex);

            if (leftElement instanceof Integer)
            {
                comparisonValue = compareIntegers((int) leftElement, (int) rightElement);
            }
            else if (leftElement instanceof Double)
            {
                comparisonValue = compareDoubles((double) leftElement, (double) rightElement);
            }
            else if (leftElement instanceof String)
            {
                comparisonValue = compareStrings((String) leftElement, (String) rightElement);
            } else
            {
                comparisonValue = Utils.compareDates((LocalDate) leftElement, (LocalDate) rightElement);
            }

            if (comparisonValue == 1)
            {
                sourceList.set(currentIndex, leftSubList.get(leftIndex));
                helperList.set(currentIndex, leftHelperList.get(leftIndex));
                leftIndex++;
            } else
            {
                sourceList.set(currentIndex, rightSubList.get(rightIndex));
                helperList.set(currentIndex, rightHelperList.get(rightIndex));
                rightIndex++;
            }
            currentIndex++;
        }

        while (leftIndex < leftSubList.size())
        {
            sourceList.set(currentIndex, leftSubList.get(leftIndex));
            helperList.set(currentIndex, leftHelperList.get(leftIndex));
            leftIndex++;
            currentIndex++;
        }

        while (rightIndex < rightSubList.size())
        {
            sourceList.set(currentIndex, rightSubList.get(rightIndex));
            helperList.set(currentIndex, rightHelperList.get(rightIndex));
            rightIndex++;
            currentIndex++;
        }

    }

    private <T> List<T> fillSubList(int low, int high, List<T> source)
    {
        List<T> subList = new ArrayList<>();

        for (int i = 0; i < high - low + 1; i++)
        {
            subList.add(source.get(i + low));
        }

        return subList;
    }

    private <U> List<U> fillHelperList (int low, int high, List<U> helper)
    {
        List<U> helperList = new ArrayList<>();

        for (int i = 0; i < high - low + 1; i++)
        {
            helperList.add(helper.get(i + low));
        }

        return helperList;
    }

    private int compareIntegers(int value1, int value2)
    {

        return value1 <= value2 ? 1 : -1;

    }

    private int compareDoubles (double value1, double value2)
    {
        return value1 <= value2 ? 1 : -1;
    }

    private int compareStrings (String s1, String s2)
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

}
