/*
 * Arjun Kharel - G00298984 - Artificial Intelligence
 */

package ie.gmit.sw.fuzzylogic;

import ie.gmit.sw.game.Enemy;
import ie.gmit.sw.game.MessageBox;
import ie.gmit.sw.game.Player;
import ie.gmit.sw.game.Weapon;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
/*
 * When player collides with an enemies, fight() method is called. please refer to fcl file for rules of the game.
 */
public class FuzzyLogic {

	public double fight(Weapon weapon, Player me, Enemy opponent) {
		String fileName = null;
		FIS fis = null;
		try {
			fileName = "flc/strengths.fcl";
			fis = FIS.load(fileName, true);
		} catch (Exception e) {
			System.out.println("Fuzzy logic fcl not found.. Please make sure fuzzylogic jar file"
					+ "is added in your classpath...");
		}

		if (fis == null) {
			System.err.println("Can't load file: '" + fileName + "'");
			//return false;
		}

		FunctionBlock functionBlock = fis.getFunctionBlock("strengths");
		//JFuzzyChart.get().chart(functionBlock);

		System.out.println("Weapon strength is: " + weapon.getWeaponStrength());
		fis.setVariable("weapon", weapon.getWeaponStrength());
		fis.setVariable("lifeOfMe", me.myLifeForce());
		fis.setVariable("lifeOfOp", opponent.getLifeForce());
		fis.evaluate();

		Double victory = fis.getVariable("victory").getValue();
		
		 me.decrementPlayerHealth(me.myLifeForce() * victory / 100);
		
		// fis.evaluate();
		System.out.println("Value: " + victory);
		System.out.println(me.myLifeForce() + " my total strength...");
		if (me.myLifeForce() <= 0) {
			MessageBox.info("Sorry you died :(,game will be closed now.", "Fuzzy Logic");
			System.exit(0); // exit the game...
			//return victory;
		}
		return victory;

		// Show output variable's chart
		/*
		 * Variable victory = functionBlock.getVariable("victory");
		 * System.out.println(victory.getDefaultValue()); System.out.println(
		 * "Value: " + victory.getValue()); JFuzzyChart.get().chart(victory,
		 * victory.getDefuzzifier(), true);
		 * 
		 * }//main /*public static void main(String[] args) { Player p = new
		 * Player(); new FuzzyLogic().fight(p.randomlyPickedWeapon(),p,new
		 * Enemy()); }
		 */
	}
}
