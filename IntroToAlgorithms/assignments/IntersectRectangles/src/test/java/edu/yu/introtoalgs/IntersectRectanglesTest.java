package edu.yu.introtoalgs;

import org.junit.Test;

import static org.junit.Assert.*;

public class IntersectRectanglesTest {

    @Test
    public void overlapsR2LCR1EasyTest(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(1,1,5,2);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(0,0,2,2);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(1,1,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2LBCR1FurtherFromAxisTest(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(3,4,2,2);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,2,2,3);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(3,4,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2LBCR1OnLLTest(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(2,2,2,3);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,1,1,2);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,2,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2InLCornerOfR1Test(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(2,2,2,3);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,2,1,1);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,2,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsOnBottomOfR1andRBC(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(1,1,3,4);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,1,3,2);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,1,2,2), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2OverlapLCornerOfR1Test(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(2,2,2,3);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(0,2,3,1);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,2,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2LBCR1NegativeTest(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(-3,-3,3,3);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(-4,-4,3,3);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(-3,-3,2,2), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2RBCR1EasyTest(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(1,2,2,3);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,1,3,2);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,2,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2RBCR1OnRLTest(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(1,2,2,3);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,1,1,2);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,2,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2InRCornerOfR1Test(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(1,2,2,3);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,2,1,1);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,2,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2RBCR1NegativeTest(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(-5,-3,3,2);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(-3,-4,2,2);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(-3,-3,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2TLCR1Test(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(2,1,4,2);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(1,2,2,3);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,2,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2InsideTLCR1Test(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(2,2,3,2);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,2,1,1);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,2,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2OnLLegTLCR1Test(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(2,1,2,2);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,2,1,5);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,2,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2OnTRCR1Test(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(1,1,3,2);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(3,2,5,5);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(3,2,1,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2InsideTRCR1Test(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(1,1,3,4);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,3,2,2);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,3,2,2), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2OnTRCAndOutR1Test(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(1,1,3,3);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,2,6,3);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,2,2,2), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2OnRLTRCR1Test(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(1,1,3,4);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,3,2,6);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,3,2,2), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void overlapsR2InBottomLineR1Test(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(2,2,4,3);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,2,1,6);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(2,2,1,3), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void mosheTest1(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(-5,1,4,4);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(2,-5,2,6);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(0,0,-1,-1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void samTest1(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(-5,-1,4,4);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(-4,0,2,1);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(-4,0,2,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void samTest2(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(-5,-1,4,4);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(-5,0,2,1);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(-5,0,2,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void samTest3(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(-5,-1,4,4);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(-3,0,2,1);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(-3,0,2,1), IntersectRectangles.intersect(r1,r2));
    }

    @Test
    public void samTest4(){
        IntersectRectangles.Rectangle r1 = new IntersectRectangles.Rectangle(-5,-1,4,4);
        IntersectRectangles.Rectangle r2 = new IntersectRectangles.Rectangle(-1,-2,1,1);

        System.out.println(r1);
        System.out.println(r2);
        System.out.println("Overlapping Rectangle: " + IntersectRectangles.intersect(r1,r2));
        assertEquals(new IntersectRectangles.Rectangle(-1,-1,0,0), IntersectRectangles.intersect(r1,r2));
    }

}