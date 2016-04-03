package ie.gmit.sw.game;

public class EnemyMovement implements Runnable {
	private Enemy enemy = null;

	public EnemyMovement(Enemy enemy) {
		this.enemy = enemy;

	}

	@Override
	public void run() {
		updatePath();
	}

	private void updatePath() {
		System.out.println(enemy.getRow() + " : " + enemy.getCol());
		System.out.println(enemy.getComputedPaths().size());

		for (int k = 0; k < enemy.getComputedPaths().size(); k++) {
			try {
				enemy.updatePath(enemy.getComputedPaths().get(k).getRow(), enemy.getComputedPaths().get(k).getCol());
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
	}

}
