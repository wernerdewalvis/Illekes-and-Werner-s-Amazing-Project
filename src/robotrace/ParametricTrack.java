
package robotrace;

/**
 * Implementation of RaceTrack, creating a track from a parametric formula
 */
public class ParametricTrack extends RaceTrack {
    
    @Override
    protected Vector getPoint(double t) {
        Vector v = new Vector(10*Math.cos(2*t*Math.PI), 
        14*Math.sin(2*t*Math.PI), 1); 
        return v;

    }

    @Override
    protected Vector getTangent(double t) {
        Vector v = new Vector(-Math.PI*20*Math.sin(2*t*Math.PI),
        Math.PI*28*Math.cos(2*t*Math.PI), 0); 
        return v;

    }
    
}
