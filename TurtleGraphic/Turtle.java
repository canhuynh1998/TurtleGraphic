package TurtleGraphic;
import java.awt.Color;
import java.util.*;
import java.util.List;
import tools.*;
public class Turtle extends Bean {
    public static Integer WORLD_SIZE = 250; // height & width of the world (& view)
    private Point currentPosition;
    private List<Point> path;
    private Color color;
    private Heading headTo;
    private boolean penIsDown;

    public Turtle(){
        currentPosition = new Point(35,35);
        path = new ArrayList<Point>();
        //path.add(currentPosition);
        penIsDown = true;
        color = Color.BLACK;
    }
    public void setPenIsDown() {
        //Set pen to true if false else true
        penIsDown = !penIsDown;
        color = penIsDown ? Color.BLACK : Color.BLUE;
        if(penIsDown){
            path.add(currentPosition);
        }else{
            currentPosition.setEndPoint(true);
        }
        firePropertyChange(null, null, null);
    }
    public Point getCurrentPosition(){return this.currentPosition;}

//    public Point getPrevious() {
//        return this.previous;
//    }

    public void setColor(Color color){
        this.color=color;
    }
    public Color getColor() { return color; }

    public void turn(Heading direction){ headTo = direction; }  //Set the moving direction of the turtle

    public List<Point> getList(){ return path; }

    public void move(int steps){
        if(penIsDown){
            path.add(currentPosition);
        }
        int newXpos = currentPosition.getxCoord();
        int newYpos = currentPosition.getyCoord();
        System.out.println(steps);
        Point previous = new Point(newXpos,newYpos);
        if(headTo == Heading.SOUTH){
            newYpos = newYpos + steps > WORLD_SIZE ?
                    newYpos + steps - WORLD_SIZE: newYpos +steps;
        }else if(headTo == Heading.NORTH){
            newYpos = newYpos - steps < 0 ?
                    newYpos - steps + WORLD_SIZE: newYpos - steps;
        }else if(headTo == Heading.EAST){
            newXpos =  newXpos + steps > WORLD_SIZE ?
                    newXpos + steps - WORLD_SIZE : newXpos + steps;
        }else if(headTo == Heading.WEST){
            newXpos =  newXpos - steps < 0  ? newXpos - steps + WORLD_SIZE : newXpos - steps;
        }
        Point newPoint = new Point(newXpos, newYpos);
        if(penIsDown){
            path.add(newPoint);
            System.out.println(newPoint.getxCoord()+" "+newPoint.getyCoord());
            System.out.println("/*************Test***********/");
        }
        currentPosition.setyCoord(newPoint.getyCoord());
        currentPosition.setxCoord(newPoint.getxCoord());
        System.out.println(currentPosition.getxCoord()+" "+currentPosition.getyCoord());
        firePropertyChange(null, null, null);
    }

    public void move1(int steps){
        //Update the path list
        //This list will only store Point when penIsDown == true
        List<Point> oldList = path;
        Point lastPoint = currentPosition;
        int newXPosition = currentPosition.getxCoord();
        int newYPosition = currentPosition.getyCoord();
        if(headTo == Heading.SOUTH){
            if(penIsDown) {
                moveSouth(steps);
            }else{
                newYPosition = newYPosition + steps > WORLD_SIZE ?
                        newYPosition + steps - WORLD_SIZE: newYPosition +steps;
                System.out.println(newYPosition);
            }
        }else if(headTo == Heading.NORTH){
            if(penIsDown) {
                moveNorth(steps);
            }else{
                newYPosition = newYPosition - steps < 0 ?
                        newYPosition - steps + WORLD_SIZE: newYPosition - steps;
            }
        } else if (headTo == Heading.EAST) {
            if(penIsDown) {
              moveEast(steps);
            }else{
                newXPosition =  newXPosition + steps > WORLD_SIZE ?
                        newXPosition + steps - WORLD_SIZE : newXPosition + steps;
            }
        }else if(headTo == Heading.WEST){
            if(penIsDown) {
                moveWest(steps);
            }else{
                newXPosition =  newXPosition - steps < 0  ? newXPosition - steps + WORLD_SIZE : newXPosition - steps;
            }
        }
        if(penIsDown){
            lastPoint = path.get(path.size()-1);

        }else {
            lastPoint.setyCoord(newYPosition);
            lastPoint.setxCoord(newXPosition);
            lastPoint.setEndPoint(true);
        }
        currentPosition.setxCoord(lastPoint.getxCoord());
        currentPosition.setyCoord(lastPoint.getyCoord());
        firePropertyChange(null, null, null);
    }

    private void moveNorth(int yCoord){
        //Moving upward
        int start = 0;
        int newYCoord = currentPosition.getyCoord() - 1;
        int newXCoord = currentPosition.getxCoord();
        while(start < yCoord){
            if(newYCoord < 0){
                newYCoord += WORLD_SIZE ;
            }
            Point newPoint = new Point(newXCoord, newYCoord, this.color);
            newYCoord -= 1;
            path.add(newPoint);
            start++;
        }
    }

    private void moveSouth(int yCoord){
        //Moving downward
        int start = 0;
        int newYCoord = currentPosition.getyCoord() + 1;
        int newXCoord = currentPosition.getxCoord();
        while(start < yCoord){
            if(newYCoord > WORLD_SIZE){
                newYCoord -= WORLD_SIZE;
            }
            Point newPoint = new Point(newXCoord, newYCoord, this.color);
            newYCoord ++;
            path.add(newPoint);
            start++;
        }
    }

    private void moveEast(int xCoord){
        //Moving to the right
        int start = 0;
        int newYCoord = currentPosition.getyCoord();
        int newXCoord = currentPosition.getxCoord() + 1;
        while(start < xCoord){
            if(newXCoord > WORLD_SIZE){
                newXCoord -= WORLD_SIZE;
            }
            Point newPoint = new Point(newXCoord, newYCoord, this.color);
            newXCoord ++;
            path.add(newPoint);
            start++;
        }
    }

    private void moveWest(int xCoord){
        //Moving to the left
        int start = 0;
        int newYCoord = currentPosition.getyCoord();
        int newXCoord = currentPosition.getxCoord() - 1;
        while(start < xCoord){
            if(newXCoord < 0){
                newXCoord += WORLD_SIZE;
            }
            Point newPoint = new Point(newXCoord, newYCoord, this.color);
            newXCoord --;
            path.add(newPoint);
            start++;
        }
    }

    public static void main(String[] args){
        Turtle turtle = new Turtle();
        System.out.println(turtle.getList().size());
        turtle.turn(Heading.NORTH);
        System.out.println(turtle.headTo);
        turtle.move(10);
        for(Point point: turtle.path){
            System.out.println(point.getxCoord()+" "+point.getyCoord());
        }

//        System.out.println(turtle.currentPosition.getyCoord());
//        System.out.println();
//        turtle.turn(Heading.SOUTH);
//        turtle.move1(1);
//        System.out.println(turtle.currentPosition.getxCoord());
//        System.out.println(turtle.currentPosition.getyCoord());
//        System.out.println();
//        turtle.turn(Heading.EAST);
//        turtle.move1(10);
//        System.out.println(turtle.currentPosition.getxCoord());
//        System.out.println(turtle.currentPosition.getyCoord());
//        System.out.println();
//        turtle.turn(Heading.WEST);
//        turtle.move1(18);
//        System.out.println(turtle.currentPosition.getxCoord());
//        System.out.println(turtle.currentPosition.getyCoord());
//        System.out.println();
        System.out.println(turtle.getList().size());
    }
}
