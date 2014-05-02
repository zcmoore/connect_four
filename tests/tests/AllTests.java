package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestComputerPlayer.class, TestConsolePlayer.class,
		TestGame.class, TestRandomPlayer.class })
public class AllTests
{
	
}
