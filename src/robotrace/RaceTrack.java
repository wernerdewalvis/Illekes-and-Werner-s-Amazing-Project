package robotrace;

import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL2.*;

/**
 * Implementation of a race track that is made from Bezier segments.
 */
abstract class RaceTrack {
    
    /** The width of one lane. The total width of the track is 4 * laneWidth. */
    private final static float laneWidth = 1.22f;
    
    
    
    /**
     * Constructor for the default track.
     */
    public RaceTrack() {
    }


    
    /**
     * Draws this track, based on the control points.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        Textures.track.bind(gl);
        Textures.track.enable(gl);
        //gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        //gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        
        gl.glBegin(GL_TRIANGLE_STRIP);
            int numberOfVectors = 150;
            for(int j = -1; j <= 2; j ++){
                double t = 0;
                gl.glColor3d(0.2,0,(1.0/j));
                    for(int i = 0; i <= numberOfVectors; i ++){
                        t = t + (1.0/numberOfVectors);
                        Vector point = getPoint(t);
                        gl.glTexCoord2d(0, 0);
                        gl.glVertex3d(point.x() + (j-1)*laneWidth*Math.cos(2*t*Math.PI),
                                      point.y() + (j-1)*laneWidth*Math.sin(2*t*Math.PI),
                                      1); 
                        gl.glTexCoord2d(1, 1);
                        gl.glVertex3d(point.x() + j*laneWidth*Math.cos(2*t*Math.PI),
                                      point.y() + j*laneWidth*Math.sin(2*t*Math.PI),
                                      1);
                }  
            }
        gl.glEnd();

        //outerrim
        Textures.brick.bind(gl);
        Textures.brick.enable(gl);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        
        gl.glBegin(GL_TRIANGLE_STRIP);
            double t = 0;
            gl.glColor3d(0.2,0,0.25);
                for(int i = 0; i <= numberOfVectors; i ++){
                    t = t + (1.0/numberOfVectors);
                    Vector point = getPoint(t);
                    gl.glTexCoord2d(0, 0);
                    gl.glVertex3d(point.x() + 2*laneWidth*Math.cos(2*t*Math.PI),
                                  point.y() + 2*laneWidth*Math.sin(2*t*Math.PI),
                                  1); 
                    gl.glTexCoord2d(1, 1);
                    gl.glVertex3d(point.x() + 2*laneWidth*Math.cos(2*t*Math.PI),
                                  point.y() + 2*laneWidth*Math.sin(2*t*Math.PI),
                                  -1);
                }  
            //innerrim
            t = 0;
            gl.glColor3d(0.2,0,1);
                for(int i = 0; i <= numberOfVectors; i ++){
                    t = t + (1.0/numberOfVectors);
                    Vector point = getPoint(t);
                    gl.glTexCoord2d(0, 0);
                    gl.glVertex3d(point.x() - 2*laneWidth*Math.cos(2*t*Math.PI),
                                  point.y() - 2*laneWidth*Math.sin(2*t*Math.PI),
                                  1); 
                    gl.glTexCoord2d(1, 1);
                    gl.glVertex3d(point.x() - 2*laneWidth*Math.cos(2*t*Math.PI),
                                  point.y() - 2*laneWidth*Math.sin(2*t*Math.PI),
                                  -1);
                }  
        gl.glEnd();
    }
    
    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    public Vector getLanePoint(int lane, double t){
        Vector v = new Vector (0,0,1);
        if (lane == 1){
            v.x = getPoint(t).x - 1.5*laneWidth*Math.cos(2*t*Math.PI);
            v.y = getPoint(t).y - 1.5*laneWidth*Math.sin(2*t*Math.PI);
        } else if (lane == 2){
            v.x = getPoint(t).x - 0.5*laneWidth*Math.cos(2*t*Math.PI);
            v.y = getPoint(t).y - 0.5*laneWidth*Math.sin(2*t*Math.PI);        
        } else if (lane == 3){
            v.x = getPoint(t).x + 0.5*laneWidth*Math.cos(2*t*Math.PI);
            v.y = getPoint(t).y + 0.5*laneWidth*Math.sin(2*t*Math.PI);            
        } else if (lane == 4){
            v.x = getPoint(t).x + 1.5*laneWidth*Math.cos(2*t*Math.PI);
            v.y = getPoint(t).y + 1.5*laneWidth*Math.sin(2*t*Math.PI);            
        }
        return v;
    }
    
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    public Vector getLaneTangent(int lane, double t){
        Vector v = getTangent(t);
        return v;
    }
    
    
    
    // Returns a point on the test track at 0 <= t < 1.
    protected abstract Vector getPoint(double t);

    // Returns a tangent on the test track at 0 <= t < 1.
    protected abstract Vector getTangent(double t);
}
