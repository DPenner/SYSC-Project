package gameCore;

public class Weapon extends Item {
	private int attackValue;
	
	public Weapon(String name, int weight, int attackValue){
		super(name, weight);
		this.attackValue=attackValue;
	}
	
	public int getAttackValue(){
		return attackValue;
	}
}
