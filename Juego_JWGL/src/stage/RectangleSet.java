package stage;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;

public class RectangleSet {
	
	public static List<Obstacle> getStage1(){
		List<Rectangle> recs_1 = new ArrayList<Rectangle>();
		
		Rectangle rec1_1 = new Rectangle(800, 520, 200, 30);
		Rectangle rec1_2 = new Rectangle(800, 500, 200, 50);
		Rectangle rec1_3 = new Rectangle(800, 200, 200, 290);
		Rectangle rec1_4 = new Rectangle(800, 520, 200, 30);
		Rectangle rec1_5 = new Rectangle(800, 500, 200, 50);
		Rectangle rec1_6 = new Rectangle(800, 200, 200, 290);
		
		recs_1.add(rec1_1);
		recs_1.add(rec1_2);
		recs_1.add(rec1_3);
		recs_1.add(rec1_4);
		recs_1.add(rec1_5);
		recs_1.add(rec1_6);
		
		int[] spaces = {0,70,200,250,70,200};
		for(int i = 0; i<recs_1.size(); i++){
			if(i != 0){
				recs_1.get(i).setX((float)(recs_1.get(i-1).getX()+recs_1.get(i-1).getWidth()+spaces[i]));
			}
		}
		
		Rectangle good_food1 = new Rectangle(1150,300,80,80);
		Rectangle bad_food1 = new Rectangle(rec1_4.getX()+80,rec1_4.getY()-200,80,80);
		
		List<Obstacle> obs = new ArrayList<Obstacle>();
		List<Rectangle> l1 = new ArrayList<Rectangle>();
		l1.add(rec1_1);
		List<Rectangle> l2 = new ArrayList<Rectangle>();
		l2.add(rec1_2);
		List<Rectangle> l3 = new ArrayList<Rectangle>();
		l3.add(rec1_3);
		List<Rectangle> l4 = new ArrayList<Rectangle>();
		l4.add(rec1_4);
		List<Rectangle> l5 = new ArrayList<Rectangle>();
		l5.add(rec1_5);
		List<Rectangle> l6 = new ArrayList<Rectangle>();
		l6.add(rec1_6);
		List<Rectangle> good_food = new ArrayList<Rectangle>();
		good_food.add(good_food1);
		List<Rectangle> bad_food = new ArrayList<Rectangle>();
		bad_food.add(bad_food1);
		obs.add(new Obstacle(good_food,"testdata/manzana.png",Obstacle.GOOD_FOOD));
		obs.add(new Obstacle(bad_food,"testdata/pastel.png",Obstacle.BAD_FOOD));
		obs.add(new Obstacle(l1,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(l2,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(l3,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(l4,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(l5,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(l6,"res/brick.png",Obstacle.PLATFORM));
		
		
		return obs;
	}
	
	public static List<Obstacle> getStage2(){
		//Obstacle 1
		List<Rectangle> recs_1 = new ArrayList<Rectangle>();
		
		Rectangle rec1_1 = new Rectangle(800, 500, 200, 50);
		Rectangle rec1_2 = new Rectangle(1000, 450, 200, 50);
		Rectangle rec1_3 = new Rectangle(1200, 400, 200, 50);
		recs_1.add(rec1_1);
		recs_1.add(rec1_2);
		recs_1.add(rec1_3);
		
		//obstacle 2
		List<Rectangle> recs_2 = new ArrayList<Rectangle>();
		Rectangle rec2_1 = new Rectangle(1400+200, 200, 200, 250);
		recs_2.add(rec2_1);
		
		//obstacle 3
		List<Rectangle> recs_3 = new ArrayList<Rectangle>();
		Rectangle rec3_1 = new Rectangle(rec2_1.getX()+rec2_1.getWidth()+200, 400, 200, 150);
		recs_3.add(rec3_1);
		
		//obstacle 4
		List<Rectangle> recs_4 = new ArrayList<Rectangle>();
		Rectangle rec4_1 = new Rectangle(rec3_1.getX()+rec3_1.getWidth()+90, 300, 200, 250);
		recs_4.add(rec4_1);
		
		//obstacle 4
		List<Rectangle> recs_5 = new ArrayList<Rectangle>();
		Rectangle rec5_1 = new Rectangle(rec4_1.getX()+rec4_1.getWidth()+200, 0, 50, 150);
		Rectangle rec5_2 = new Rectangle(rec4_1.getX()+rec4_1.getWidth()+100, 300, 200, 50);
		recs_5.add(rec5_1);
		recs_5.add(rec5_2);
		
		
		Rectangle good_food1 = new Rectangle(rec1_1.getX()+30,rec1_1.getY()-200,80,80);
		Rectangle bad_food1 = new Rectangle(rec4_1.getX()+30,rec4_1.getY()-200,80,80);
		
		List<Rectangle> good_food = new ArrayList<Rectangle>();
		good_food.add(good_food1);
		List<Rectangle> bad_food = new ArrayList<Rectangle>();
		bad_food.add(bad_food1);
		
		
		List<Obstacle> obs = new ArrayList<Obstacle>();
		obs.add(new Obstacle(good_food,"testdata/zanahoria.png",Obstacle.GOOD_FOOD));
		obs.add(new Obstacle(bad_food,"testdata/bebida.png",Obstacle.BAD_FOOD));
		obs.add(new Obstacle(recs_1,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(recs_2,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(recs_3,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(recs_4,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(recs_5,"res/brick.png",Obstacle.PLATFORM));
		return obs;
	}

		public static List<Obstacle> getStage3(){
		List<Rectangle> recs_3 = new ArrayList<Rectangle>();
		
		
		//escalera
		Rectangle rec3_1 = new Rectangle(800, 510, 100, 100);
		Rectangle rec3_2 = new Rectangle(800, 470, 200, 120);
		Rectangle rec3_3 = new Rectangle(800, 430, 200, 160);
		Rectangle rec3_4 = new Rectangle(800, 390, 200, 200);
		Rectangle rec3_5 = new Rectangle(800, 350, 200, 240);
		Rectangle rec3_6 = new Rectangle(800, 310, 200, 280);
		

		//cosas puntudas
		Rectangle rec3_7 = new Rectangle(800, 500, 70, 50);
			
		//perro
		Rectangle rec3_8 = new Rectangle(800, 500, 80, 60);
		
		//gato
		Rectangle rec3_9 = new Rectangle(800, 500, 80, 60);
		
		
		//meta
		Rectangle rec3_10 = new Rectangle(800, 360, 150, 200);
		
		recs_3.add(rec3_7);
		recs_3.add(rec3_8);
		recs_3.add(rec3_1);
		recs_3.add(rec3_2);
		recs_3.add(rec3_3);
		recs_3.add(rec3_4);
		recs_3.add(rec3_5);
		recs_3.add(rec3_6);
		recs_3.add(rec3_9);
		recs_3.add(rec3_10);
		
		int [] spaces ={0,200,250,0,0,0,0,0,300,300}; 
		
		for(int i = 0; i<recs_3.size(); i++){
			if(i != 0){
				recs_3.get(i).setX((float)(recs_3.get(i-1).getX()+recs_3.get(i-1).getWidth()+spaces[i]));
			}
		}
		
		Rectangle good_food1 = new Rectangle(rec3_6.getX()+30,rec3_6.getY()-200,80,80);
		
		List<Obstacle> obs = new ArrayList<Obstacle>();
		
		//cosas puntudas
		List<Rectangle> l7 = new ArrayList<Rectangle>();
		l7.add(rec3_7);
		
		//perro
		List<Rectangle> l8 = new ArrayList<Rectangle>();
		l8.add(rec3_8);
		
		//escalera
		List<Rectangle> l1 = new ArrayList<Rectangle>();
		l1.add(rec3_1);
		List<Rectangle> l2 = new ArrayList<Rectangle>();
		l2.add(rec3_2);
		List<Rectangle> l3 = new ArrayList<Rectangle>();
		l3.add(rec3_3);
		List<Rectangle> l4 = new ArrayList<Rectangle>();
		l4.add(rec3_4);
		List<Rectangle> l5 = new ArrayList<Rectangle>();
		l5.add(rec3_5);
		List<Rectangle> l6 = new ArrayList<Rectangle>();
		l6.add(rec3_6);
		
		//gato
		List<Rectangle> l9 = new ArrayList<Rectangle>();
		l9.add(rec3_9);
		
		//meta
		List<Rectangle> meta = new ArrayList<Rectangle>();
		meta.add(rec3_10);
		
		List<Rectangle> good_food = new ArrayList<Rectangle>();
		good_food.add(good_food1);
		
		obs.add(new Obstacle(good_food,"testdata/agua.png",Obstacle.WATER));
		obs.add(new Obstacle(l7,"testdata/cosaspuntudas.png",Obstacle.DANGEROUS));
		obs.add(new Obstacle(l8,"testdata/dog-icon.png",Obstacle.DANGEROUS));
		obs.add(new Obstacle(l1,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(l2,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(l3,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(l4,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(l5,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(l6,"res/brick.png",Obstacle.PLATFORM));
		obs.add(new Obstacle(l9,"testdata/dog-icon.png",Obstacle.DANGEROUS));
		obs.add(new Obstacle(meta, "testdata/runner_goal.png", Obstacle.GOAL));
		
		return obs;
	}
	
}
