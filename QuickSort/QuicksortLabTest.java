

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class QuicksortLabTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class QuicksortLabTest
{
   @Test
   public void testSort3items()
    {
        QuicksortLab q1 = new QuicksortLab();
        int[] sortme = {3,2,5};
        q1.quickSort(sortme);
        assertEquals(2, sortme[0]);
        assertEquals(5, sortme[2]);
    }
    
    @Test
    public void testSort()
    {
        QuicksortLab q1 = new QuicksortLab();
        int[] sortme = {53,24,17,81,45,68,72,76,39,21,43};
        q1.quickSort(sortme);
        assertEquals(17, sortme[0]);
        assertEquals(21, sortme[1]);
        assertEquals(24, sortme[2]);
        assertEquals(39, sortme[3]);
        assertEquals(43, sortme[4]);
        assertEquals(45, sortme[5]);
        assertEquals(53, sortme[6]);
        assertEquals(68, sortme[7]);
        assertEquals(72, sortme[8]);
        assertEquals(76, sortme[9]);
        assertEquals(81, sortme[10]);
    }

}
