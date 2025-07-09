package invoice.utils;

import invoice.interfaces.ComparatorCallBack;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortingUtil implements ComparatorCallBack
{

    public SortingUtil () { }

    public <T> void mergeSort (int low, int high, List<T> sourceList, ComparatorCallBack comparatorCallBack)
    {
        if (low >= high)
        {
            return;
        }

        int mid = (low + high) / 2;

        mergeSort(low, mid, sourceList, comparatorCallBack);
        mergeSort(mid + 1, high, sourceList, comparatorCallBack);
        merge(low, mid, high, sourceList, comparatorCallBack);
    }

    private <T> void merge (int low, int mid, int high, List<T> sourceList, ComparatorCallBack comparatorCallBack)
    {
        List<T> leftSubList = fillSubList(low, mid, sourceList);

        List<T> rightSubList = fillSubList(mid + 1, high, sourceList);

        int leftIndex = 0, rightIndex = 0;
        int currentIndex = low;

        while (leftIndex < leftSubList.size() && rightIndex < rightSubList.size())
        {
            int comparisonValue = comparatorCallBack.comparator(leftSubList.get(leftIndex), rightSubList.get(rightIndex));

            if (comparisonValue == 1)
            {
                sourceList.set(currentIndex, leftSubList.get(leftIndex));
                leftIndex++;
            } else
            {
                sourceList.set(currentIndex, rightSubList.get(rightIndex));
                rightIndex++;
            }
            currentIndex++;
        }

        while (leftIndex < leftSubList.size())
        {
            sourceList.set(currentIndex, leftSubList.get(leftIndex));
            leftIndex++;
            currentIndex++;
        }

        while (rightIndex < rightSubList.size())
        {
            sourceList.set(currentIndex, rightSubList.get(rightIndex));
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

    @Override
    public <T> int comparator (T obj1, T obj2) {
        return 0;
    }
}
