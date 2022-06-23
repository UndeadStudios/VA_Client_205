public class Entity extends Animable {

	public final void setPos(int i, int j, boolean flag) {
		if (animation != -1 && AnimationDefinition.anims[animation].anInt364 == 1)
			animation = -1;
		if (!flag) {
			int k = i - waypoint_x[0];
			int l = j - waypoint_y[0];
			if (k >= -8 && k <= 8 && l >= -8 && l <= 8) {
				if (smallXYIndex < 9)
					smallXYIndex++;
				for (int i1 = smallXYIndex; i1 > 0; i1--) {
					waypoint_x[i1] = waypoint_x[i1 - 1];
					waypoint_y[i1] = waypoint_y[i1 - 1];
					waypoint_traveled[i1] = waypoint_traveled[i1 - 1];
				}

				waypoint_x[0] = i;
				waypoint_y[0] = j;
				waypoint_traveled[0] = false;
				return;
			}
		}
		smallXYIndex = 0;
		anInt1542 = 0;
		anInt1503 = 0;
		waypoint_x[0] = i;
		waypoint_y[0] = j;
		world_x = waypoint_x[0] * 128 + occupied_tiles * 64;
		world_y = waypoint_y[0] * 128 + occupied_tiles * 64;
	}

	public final void method446() {
		smallXYIndex = 0;
		anInt1542 = 0;
	}

	public int[] hitmarkMove = new int[4];
	public int[] hitmarkTrans = new int[4];
	public int[] hitIcon = new int[4];

	public final void updateHitData(int markType, int damage, int l, int icon) {
		for (int i1 = 0; i1 < 4; i1++)
			if (damage_cycle[i1] <= l) {
				hitIcon[i1] = icon;
				hitmarkMove[i1] = 5;
				hitmarkTrans[i1] = 230;
				damage_dealt[i1] = Configuration.enable10xDamage ? damage * 10 : damage;
  				if(damage > 0 && Configuration.enable10xDamage) {
					damage_dealt[i1] += new java.util.Random().nextInt(9);
				}
				damage_marker[i1] = markType;
				damage_cycle[i1] = l + 70;
				return;
			}
	}

	public final void moveInDir(boolean flag, int i) {
		int j = waypoint_x[0];
		int k = waypoint_y[0];
		if (i == 0) {
			j--;
			k++;
		}
		if (i == 1)
			k++;
		if (i == 2) {
			j++;
			k++;
		}
		if (i == 3)
			j--;
		if (i == 4)
			j++;
		if (i == 5) {
			j--;
			k--;
		}
		if (i == 6)
			k--;
		if (i == 7) {
			j++;
			k--;
		}
		if (animation != -1 && AnimationDefinition.anims[animation].anInt364 == 1)
			animation = -1;
		if (smallXYIndex < 9)
			smallXYIndex++;
		for (int l = smallXYIndex; l > 0; l--) {
			waypoint_x[l] = waypoint_x[l - 1];
			waypoint_y[l] = waypoint_y[l - 1];
			waypoint_traveled[l] = waypoint_traveled[l - 1];
		}
		waypoint_x[0] = j;
		waypoint_y[0] = k;
		waypoint_traveled[0] = flag;
	}

	public int entScreenX;
	public int entScreenY;
	public final int index = -1;

	public boolean isVisible() {
		return false;
	}

	Entity() {
		waypoint_x = new int[10];
		waypoint_y = new int[10];
		engaged_entity_id = -1;
		rotation = 32;
		running_animation_id = -1;
		height = 200;
		idle_animation_id = -1;
		standing_turn_animation_id = -1;
		damage_dealt = new int[4];
		damage_marker = new int[4];
		damage_cycle = new int[4];
		queued_animation_id = -1;
		graphic_id = -1;
		animation = -1;
		game_tick_status = -1000;
		message_cycle = 100;
		occupied_tiles = 1;
		dynamic = false;
		waypoint_traveled = new boolean[10];
		walk_animation_id = -1;
		turn_around_animation_id = -1;
		pivot_right_animation_id = -1;
		pivot_left_animation_id = -1;
	}

	public final int[] waypoint_x;
	public final int[] waypoint_y;
	public int engaged_entity_id;
	int anInt1503;
	int rotation;
	int running_animation_id;
	public String textSpoken;
	public int height;
	public int turnDirection;
	int idle_animation_id;
	int standing_turn_animation_id;
	int anInt1513;
	final int[] damage_dealt;
	final int[] damage_marker;
	final int[] damage_cycle;
	int queued_animation_id;
	int anInt1518;
	int anInt1519;
	int graphic_id;
	int anInt1521;
	int anInt1522;
	int anInt1523;
	int anInt1524;
	int smallXYIndex;
	public int animation;
	int anInt1527;
	int anInt1528;
	int anInt1529;
	int anInt1530;
	int anInt1531;
	public int game_tick_status;
	public int currentHealth;
	public int maxHealth;
	int message_cycle;
	int anInt1537;
	int anInt1538;
	int anInt1539;
	int occupied_tiles;
	boolean dynamic;
	int anInt1542;
	int anInt1543;
	int anInt1544;
	int anInt1545;
	int anInt1546;
	int anInt1547;
	int anInt1548;
	int anInt1549;
	public int world_x;
	public int world_y;
	int current_rotation;
	final boolean[] waypoint_traveled;
	int walk_animation_id;
	int turn_around_animation_id;
	int pivot_right_animation_id;
	int pivot_left_animation_id;

	public int currentAnim;
	public int nextAnimFrame;
	public int nextIdleAnimFrame;
	public int nextSpotAnimFrame;
}
