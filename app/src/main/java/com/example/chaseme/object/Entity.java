package com.example.chaseme.object;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

public class Entity {
    private int xPos, yPos;
    private int radius;
    private Paint color;
    private Bitmap bitmap;
    private boolean haveCollision;


    public Entity(int xPos, int yPos, int radius) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
    }

    public Entity(int xPos, int yPos, int radius, Paint color) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
        this.color = color;
    }


    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isHaveCollision() {
        return haveCollision;
    }

    public void setHaveCollision(boolean haveCollision) {
        this.haveCollision = haveCollision;
    }


    /**
     * Transforme un Drawable en Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * Vérifie que la hitbox d'une entité soit en contact avec celle de l'entité courante.
     * La hitbox est ici représenté par cercle.
     * @param entity L'entité avec la quelle on veux verifier
     * @return Retourne vrai si les deux entités sont en contact.
     */
    public boolean intersectWith(Entity entity)
    {
        int distSq = (xPos - entity.getxPos()) * (xPos - entity.getxPos()) +
                (yPos - entity.getyPos()) * (yPos - entity.getyPos());
        int radSumSq = (radius + entity.getRadius()) * (radius + entity.getRadius());
        if (distSq > radSumSq)
            return false;
        return true;
    }

    /**
     * Vérifie que la hitbox d'une entité soit en contact avec celle de l'entité courante.
     * La hitbox est ici représenté par cercle avec un radius custom.
     * @param entity L'entité avec la quelle on veux verifier
     * @param customEntityRadius Le radius de l'entité entrée en paramètre
     * @return Retourne vrai si les deux entités sont en contact.
     */
    public boolean intersectWith(Entity entity, int customEntityRadius)
    {
        int distSq = (xPos - entity.getxPos()) * (xPos - entity.getxPos()) +
                (yPos - entity.getyPos()) * (yPos - entity.getyPos());
        int radSumSq = (radius + customEntityRadius) * (radius + customEntityRadius);
        if (distSq > radSumSq)
            return false;
        return true;
    }

    /**
     * Dessine le Bitmap, si présent, à l'aide du Canvas.
     * @param canvas
     */
    public void draw(Canvas canvas) {
        if(bitmap != null) {
            canvas.drawBitmap(bitmap, xPos - bitmap.getWidth()/2, yPos - bitmap.getHeight()/2, new Paint());
        }
    }
}
