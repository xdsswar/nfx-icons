/*
 * Copyright © 2025. XTREME SOFTWARE SOLUTIONS
 *
 * All rights reserved. Unauthorized use, reproduction, or distribution
 * of this software or any portion of it is strictly prohibited and may
 * result in severe civil and criminal penalties. This code is the sole
 * proprietary of XTREME SOFTWARE SOLUTIONS.
 *
 * Commercialization, redistribution, and use without explicit permission
 * from XTREME SOFTWARE SOLUTIONS, are expressly forbidden.
 */

package xss.it.nfx.icons;

import javafx.scene.text.Font;

import java.util.ResourceBundle;

/**
 * @author XDSSWAR
 * Created on 02/22/2025
 */
public final class Icon extends AbstractIcon {
    /**
     * A {@link ResourceBundle} containing icon names and their corresponding unicode values.
     */
    private static final ResourceBundle ICONS;

    /**
     * Constructs a default {@code Icon} instance.
     */
    public Icon() {
        super();
    }

    /**
     * Constructs an {@code Icon} instance with the specified icon.
     *
     * @param icon the icon name or unicode representation
     */
    public Icon(String icon) {
        super(icon);
    }


    /**
     * Returns the default icon representation.
     *
     * @return the default icon as a {@link String}
     */
    @Override
    public String defaultIcon() {
        return "AIRLINE_SEAT_FLAT_ANGLED";
    }

    /**
     * Returns a {@link ResourceBundle} containing icon names and their corresponding unicode values.
     * Implementing classes must provide the resource bundle containing the icon mappings.
     *
     * @return the {@link ResourceBundle} containing icon data
     */
    @Override
    protected  ResourceBundle resource() {
        return ICONS;
    }

    /*
     * Load the font and icons
     */
    static {
        try {
            /*
             * Font icons
             */
            Font.loadFont(AbstractIcon.class.getResourceAsStream("/xss/it/nfx/icons/525icons.ttf"), DEF_SIZE);
            Font.loadFont(AbstractIcon.class.getResourceAsStream("/xss/it/nfx/icons/emojione-svg.otf"), DEF_SIZE);
            Font.loadFont(AbstractIcon.class.getResourceAsStream("/xss/it/nfx/icons/fa-brands-400.ttf"), DEF_SIZE);
            Font.loadFont(AbstractIcon.class.getResourceAsStream("/xss/it/nfx/icons/feather.ttf"), DEF_SIZE);
            Font.loadFont(AbstractIcon.class.getResourceAsStream("/xss/it/nfx/icons/fontawesome-webfont.ttf"), DEF_SIZE);
            Font.loadFont(AbstractIcon.class.getResourceAsStream("/xss/it/nfx/icons/m-webfont.ttf"), DEF_SIZE);
            Font.loadFont(AbstractIcon.class.getResourceAsStream("/xss/it/nfx/icons/MaterialIcons-Regular.ttf"), DEF_SIZE);
            Font.loadFont(AbstractIcon.class.getResourceAsStream("/xss/it/nfx/icons/octicons.ttf"), DEF_SIZE);
            Font.loadFont(AbstractIcon.class.getResourceAsStream("/xss/it/nfx/icons/weathericons-regular-webfont.ttf"), DEF_SIZE);

            /*
             * Load Icon
             */
            ICONS = ResourceBundle.getBundle("xss/it/nfx/icons/all");

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
