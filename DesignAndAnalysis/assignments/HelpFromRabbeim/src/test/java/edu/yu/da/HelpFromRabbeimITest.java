package edu.yu.da;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import edu.yu.da.HelpFromRabbeimI.*;

import java.util.*;

public class HelpFromRabbeimITest {

    @Test
    public void leffTest(){
        final Rebbe rebbe0 = new Rebbe (0 , List.of(HelpTopics.BAVA_KAMMA));
        final Rebbe rebbe1 = new Rebbe ( 1 , List.of(HelpTopics.BAVA_KAMMA));
        final Map<HelpTopics , Integer> requestedHelp = new HashMap<>();
        requestedHelp.put(HelpTopics.BAVA_KAMMA, 2);

        final HelpFromRabbeimI hfr = new HelpFromRabbeim();
        final List<Rebbe> rabbeim = List.of(rebbe0, rebbe1);
        final Map<Integer, HelpTopics> schedule = hfr.scheduleIt(rabbeim, requestedHelp);
    }

    @Test
    public void altLeffTest(){
        final Rebbe rebbe0 = new Rebbe (0 , List.of(HelpTopics.BAVA_KAMMA));
        final Rebbe rebbe1 = new Rebbe ( 1 , List.of(HelpTopics.BAVA_KAMMA));
        final Map<HelpTopics , Integer> requestedHelp = new HashMap<>();
        requestedHelp.put(HelpTopics.BAVA_KAMMA, 1);

        final HelpFromRabbeimI hfr = new HelpFromRabbeim();
        final List<Rebbe> rabbeim = List.of(rebbe0, rebbe1);
        final Map<Integer, HelpTopics> schedule = hfr.scheduleIt(rabbeim, requestedHelp);
    }

    @Test
    public void altLeffTest1(){
        final Rebbe rebbe0 = new Rebbe (0 , List.of(HelpTopics.BAVA_KAMMA));
        final Rebbe rebbe1 = new Rebbe ( 1 , List.of(HelpTopics.BAVA_KAMMA));
        final Map<HelpTopics , Integer> requestedHelp = new HashMap<>();
        requestedHelp.put(HelpTopics.BAVA_KAMMA, 3);

        final HelpFromRabbeimI hfr = new HelpFromRabbeim();
        final List<Rebbe> rabbeim = List.of(rebbe0, rebbe1);
        final Map<Integer, HelpTopics> schedule = hfr.scheduleIt(rabbeim, requestedHelp);
    }

    @Test
    public void barelyATest(){
        final Rebbe rebbe0 = new Rebbe (0 , List.of(HelpTopics.BAVA_KAMMA, HelpTopics.MUSSAR));
        final Rebbe rebbe1 = new Rebbe ( 1 , List.of(HelpTopics.MUSSAR));
        final Rebbe rebbe2 = new Rebbe ( 2 , List.of(HelpTopics.BAVA_KAMMA));
        final Rebbe rebbe3 = new Rebbe ( 3 , List.of(HelpTopics.MUSSAR));
        final Rebbe rebbe4 = new Rebbe ( 4 , List.of(HelpTopics.MUSSAR, HelpTopics.SHABBOS));
        final Rebbe rebbe5 = new Rebbe ( 5 , List.of(HelpTopics.SHABBOS));

        final Map<HelpTopics , Integer> requestedHelp = new HashMap<>();
        requestedHelp.put(HelpTopics.BAVA_KAMMA, 2);
        requestedHelp.put(HelpTopics.MUSSAR, 3);
        requestedHelp.put(HelpTopics.SHABBOS, 1);

        final HelpFromRabbeimI hfr = new HelpFromRabbeim();
        final List<Rebbe> rabbeim = List.of(rebbe0, rebbe1, rebbe2, rebbe3, rebbe4, rebbe5);
        final Map<Integer, HelpTopics> schedule = hfr.scheduleIt(rabbeim, requestedHelp);
    }

    @Test
    public void altBarelyATest(){
        final Rebbe rebbe0 = new Rebbe (0 , List.of(HelpTopics.BAVA_KAMMA, HelpTopics.MUSSAR));
        final Rebbe rebbe1 = new Rebbe ( 1 , List.of(HelpTopics.MUSSAR));
        final Rebbe rebbe2 = new Rebbe ( 2 , List.of(HelpTopics.BAVA_KAMMA));
        final Rebbe rebbe3 = new Rebbe ( 3 , List.of(HelpTopics.MUSSAR));
        final Rebbe rebbe4 = new Rebbe ( 4 , List.of(HelpTopics.MUSSAR, HelpTopics.SHABBOS));
        final Rebbe rebbe5 = new Rebbe ( 5 , List.of(HelpTopics.SHABBOS));
        final Rebbe rebbe6 = new Rebbe ( 6 , List.of(HelpTopics.SHABBOS));

        final Map<HelpTopics , Integer> requestedHelp = new HashMap<>();
        requestedHelp.put(HelpTopics.BAVA_KAMMA, 2);
        requestedHelp.put(HelpTopics.MUSSAR, 3);
        requestedHelp.put(HelpTopics.SHABBOS, 1);

        final HelpFromRabbeimI hfr = new HelpFromRabbeim();
        final List<Rebbe> rabbeim = List.of(rebbe0, rebbe1, rebbe2, rebbe3, rebbe4, rebbe5, rebbe6);
        final Map<Integer, HelpTopics> schedule = hfr.scheduleIt(rabbeim, requestedHelp);
    }

    @Test
    public void altBarelyATest1(){
        final Rebbe rebbe0 = new Rebbe (0 , List.of(HelpTopics.BAVA_KAMMA, HelpTopics.MUSSAR));
        final Rebbe rebbe1 = new Rebbe ( 1 , List.of(HelpTopics.MUSSAR));
        final Rebbe rebbe2 = new Rebbe ( 2 , List.of(HelpTopics.BAVA_KAMMA));
        final Rebbe rebbe3 = new Rebbe ( 3 , List.of(HelpTopics.MUSSAR));
        final Rebbe rebbe4 = new Rebbe ( 4 , List.of(HelpTopics.MUSSAR, HelpTopics.SHABBOS));
        final Rebbe rebbe5 = new Rebbe ( 5 , List.of(HelpTopics.SHABBOS));
        final Rebbe rebbe6 = new Rebbe ( 6 , List.of(HelpTopics.SHABBOS));

        final Map<HelpTopics , Integer> requestedHelp = new HashMap<>();
        requestedHelp.put(HelpTopics.BAVA_KAMMA, 2);
        requestedHelp.put(HelpTopics.MUSSAR, 3);
        requestedHelp.put(HelpTopics.SHABBOS, 2);

        final HelpFromRabbeimI hfr = new HelpFromRabbeim();
        final List<Rebbe> rabbeim = List.of(rebbe0, rebbe1, rebbe2, rebbe3, rebbe4, rebbe5, rebbe6);
        final Map<Integer, HelpTopics> schedule = hfr.scheduleIt(rabbeim, requestedHelp);
    }

    @Test
    public void altBarelyATest2(){
        final Rebbe rebbe0 = new Rebbe (0 , List.of(HelpTopics.BAVA_KAMMA, HelpTopics.MUSSAR));
        final Rebbe rebbe1 = new Rebbe ( 1 , List.of(HelpTopics.MUSSAR));
        final Rebbe rebbe2 = new Rebbe ( 2 , List.of(HelpTopics.BAVA_KAMMA));
        final Rebbe rebbe3 = new Rebbe ( 3 , List.of(HelpTopics.MUSSAR));
        final Rebbe rebbe4 = new Rebbe ( 4 , List.of(HelpTopics.MUSSAR, HelpTopics.SHABBOS));
        final Rebbe rebbe5 = new Rebbe ( 5 , List.of(HelpTopics.SHABBOS));
        final Rebbe rebbe6 = new Rebbe ( 6 , List.of(HelpTopics.SHABBOS));

        final Map<HelpTopics , Integer> requestedHelp = new HashMap<>();
        requestedHelp.put(HelpTopics.BAVA_KAMMA, 2);
        requestedHelp.put(HelpTopics.MUSSAR, 3);
        requestedHelp.put(HelpTopics.SHABBOS, 4);

        final HelpFromRabbeimI hfr = new HelpFromRabbeim();
        final List<Rebbe> rabbeim = List.of(rebbe0, rebbe1, rebbe2, rebbe3, rebbe4, rebbe5, rebbe6);
        final Map<Integer, HelpTopics> schedule = hfr.scheduleIt(rabbeim, requestedHelp);
    }

    @Test
    public void newTest(){
        final Rebbe rebbe0 = new Rebbe (0 , List.of(HelpTopics.BAVA_KAMMA));
        final Rebbe rebbe1 = new Rebbe ( 1 , List.of(HelpTopics.BAVA_KAMMA));
        final Map<HelpTopics , Integer> requestedHelp = new HashMap<>();
        requestedHelp.put(HelpTopics.BAVA_KAMMA, 2);
        requestedHelp.put(HelpTopics.SHABBOS, 2);

        final HelpFromRabbeimI hfr = new HelpFromRabbeim();
        final List<Rebbe> rabbeim = List.of(rebbe0, rebbe1);
        final Map<Integer, HelpTopics> schedule = hfr.scheduleIt(rabbeim, requestedHelp);
    }

}