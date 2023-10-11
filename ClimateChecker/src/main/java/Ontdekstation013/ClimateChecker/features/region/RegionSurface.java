/*
package Ontdekstation013.ClimateChecker.features.region;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class RegionSurface {

    public List<double[]> points;

    //checks if point is contained in the region.
    public boolean contains(double x, double y)
    {
        //creating the region in Path2D
        Path2D path = new Path2D.Double();

        path.moveTo(points.get(0)[0], points.get(0)[1]);

        //adding all coordinates
        for(int i = 1; i < points.size(); i++)
        {
            path.lineTo(points.get(i)[0], points.get(i)[1]);
        }
        path.closePath();

        if(path.contains(x,y)) return true;
        return false;
    }


    public void addPoint(double x, double y)
    {
        points.add(new double[]{x,y});
    }

    public RegionSurface(List<double[]> points)
    {
        this.points = points;
    }


    public RegionSurface()
    {
        points = new ArrayList<>();
    }


}
*/
