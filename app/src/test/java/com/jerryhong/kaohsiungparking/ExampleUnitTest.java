package com.jerryhong.kaohsiungparking;

import com.jerryhong.kaohsiungparking.ui.map.MainViewModel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testMainViewModel(){
        MainViewModel viewModel = new MainViewModel();
        assertEquals(10, viewModel.testAdd(5, 6));
    }
}