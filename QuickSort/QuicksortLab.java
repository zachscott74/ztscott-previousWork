import java.util.Vector;
/**
 * Modify this implementation of Quicksort.
 * Change the private method quicksort() so it is implemented WITHOUT the two vectors,
 * and it their place uses a single array named "extra".
 * 
 */
public class QuicksortLab
{
    int[] items = new int[100];

    public void quickSort(int[] arrayToSort)
    {
        items = arrayToSort;
        quicksort(0, items.length - 1);
        for (int index = 0; index <= items.length-1; index ++)
        {
            System.out.print(items[index] + " ");
        }
        System.out.println();
    }

    @SuppressWarnings("unchecked") 
    private void quicksort(int from, int to)
    {
        int pivot = from;
        int pivotValue = items[pivot];

        System.out.print("(" + from + "," + to + ") ");
        for (int index = from; index <= to; index ++)
        {
            System.out.print(items[index] + " ");
        }
        System.out.println();    

        int[] partitions = new int[(to-from)+1];
        int right = to;
        int left = from;
        //Vector leftPartition = new Vector();
        //Vector rightPartition = new Vector();

        for (int i = from; i <= to; i++)
        {
            if (i != pivot)
            {
                if (items[i] < pivotValue)
                {
                    partitions[left] = items[i];
                    left++;
                    //leftPartition.addElement(new Integer(items[i]));
                }
                else
                {
                    partitions[right] = items[i];
                    right--;
                    //rightPartition.insertElementAt(new Integer(items[i]),0);
                }
            }
        }

        for (int i = from; i <= to; i++)
        {
            if ( (i-from) < left)
            {
                items[i] = partitions[i-from];
                //items[i] = ((Integer)leftPartition.elementAt(i-from)).intValue();
            }
            else if ( (i-from) == left)
            {
                items[i] = pivotValue;
            }
            else
            {
                items[i] = partitions[i-from];
                //items[i] =  ((Integer)rightPartition.elementAt(i-from-leftPartition.size()-1)).intValue();
            }
        }

        if (left > 1)
        {
            quicksort(from, from+left-1);
        }
        if (to-from-right > 1)
        {
            quicksort(left+from, left+from+(to-from-right));
        }

    }

}
