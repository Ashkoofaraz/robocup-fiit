package sk.fiit.jim.agent.moves.ik;

public class IkMain
{
    public static void main(String[] args)
    {
        HeadIk he = new HeadIk();
        System.out.println(Math.toDegrees(he.getTheta2(53.9, 10.0, 67.9)));
    }
}
