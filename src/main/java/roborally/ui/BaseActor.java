package roborally.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
        import com.badlogic.gdx.graphics.g2d.Batch;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.TextureRegion;
        import com.badlogic.gdx.graphics.Color;
        import com.badlogic.gdx.math.Rectangle;
        import com.badlogic.gdx.math.Polygon;
        import com.badlogic.gdx.math.MathUtils;
        import com.badlogic.gdx.math.Intersector;
        import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;

        import java.awt.*;

public class BaseActor extends Actor {

    public TextureRegion region;
    public Polygon boundingPolygon;
    public BaseActor() {
        region = new TextureRegion();
        boundingPolygon = null;
    }

    public void setTexture(Texture t){
        int w = t.getWidth();
        int h = t.getHeight();
        setWidth( w );
        setHeight( h );
        region.setRegion(t);
    }

    public void act(float dt)
    {
        super.act( dt );
    }

    public void draw(Batch batch, float parentAlpha)
    {
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);
        if ( isVisible() )
            batch.draw( region, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );
    }

    public void setRectangleBoundary()
    {
        float w = getWidth();
        float h = getHeight();
        float[] vertices = {0,0, w,0, w,h, 0,h};
        boundingPolygon = new Polygon(vertices);
        boundingPolygon.setOrigin( getOriginX(), getOriginY() );
    }

    public void setOriginCenter()
    {
        if ( getWidth() == 0 )
            System.err.println("error: actor size not set");
        setOrigin( getWidth()/2, getHeight()/2 );
    }
}
