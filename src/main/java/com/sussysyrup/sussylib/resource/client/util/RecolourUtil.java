package com.sussysyrup.sussylib.resource.client.util;

import com.sussysyrup.sussylib.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class RecolourUtil {

        public static InputStream recolour(BufferedImage image, ColourScheme colourScheme) {

                ColorModel cm = image.getColorModel();
                boolean bool = image.isAlphaPremultiplied();
                WritableRaster raster = image.copyData(image.getRaster().createCompatibleWritableRaster());

                BufferedImage bi = new BufferedImage(cm, raster, bool, null);
                Color color;
                for(int h = 0; h < bi.getHeight(); h++)
                {
                        for(int w = 0; w < bi.getWidth(); w++)
                        {
                                color = new Color(bi.getRGB(w, h));
                                if(colourScheme.template().contains(color))
                                {
                                        bi.setRGB(w, h, colourScheme.colours().get(colourScheme.template().indexOf(color)).getRGB());
                                }
                        }
                }

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                try {
                        ImageIO.write(bi, "png", os);
                        InputStream is = new ByteArrayInputStream(os.toByteArray());

                        return is;
                } catch (Exception e)
                {
                        Main.LOGGER.error(e.toString());
                        return null;
                }
        }
}
