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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.css.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author XDSSWAR
 * Created on 02/21/2025
 */
public abstract class AbstractIcon extends Text {
    /**
     * The default size for icons in pixels.
     */
    protected static final double DEF_SIZE = 12;

    /**
     * Font family
     */
    private String family = null;

    /**
     * Constructs an {@code AbstractIcon} instance.
     * Calls {@code initialize()} to set up the icon properties.
     */
    protected AbstractIcon() {
        super();
        initialize();
    }

    /**
     * Constructs an {@code AbstractIcon} instance with the specified icon.
     * Calls the default constructor and sets the icon.
     *
     * @param icon the icon name
     */
    protected AbstractIcon(String icon){
        this();
        setIcon(icon);
    }

    /**
     * Initializes the component.
     * This method can be overridden to provide custom initialization logic.
     */
    private void initialize() {
        getStyleClass().add("nfx-icon");

        handleIcon(getIcon());
        iconProperty().addListener((
                obs,
                o,
                icon) -> handleIcon(icon)
        );

        // handleSize(getSize());
        sizeProperty().addListener((
                obs,
                o,
                size) -> handleSize(size.doubleValue())
        );
    }

    /**
     * The property representing the icon as a string.
     */
    private StringProperty icon;

    /**
     * Gets the current icon value.
     *
     * @return the icon as a {@link String}
     */
    public final String getIcon() {
        return iconProperty().get();
    }

    /**
     * Returns the {@code StringProperty} representing the icon.
     * Initializes the property if it is not already set.
     *
     * @return the icon property
     */
    public final StringProperty iconProperty() {
        if (icon == null) {
            icon = new SimpleStyleableStringProperty(
                    Styleables.ICON,
                    this,
                    "icon",
                    defaultIcon()
            );
        }
        return icon;
    }

    /**
     * Sets the icon value.
     *
     * @param icon the new icon as a {@link String}
     */
    public final void setIcon(String icon) {
        iconProperty().set(icon);
    }

    /**
     * The property representing the size of the icon.
     */
    private DoubleProperty size;

    /**
     * Gets the current size of the icon.
     *
     * @return the icon size as a {@code double}
     */
    public final double getSize() {
        return sizeProperty().get();
    }

    /**
     * Returns the {@code DoubleProperty} representing the icon size.
     * Initializes the property if it is not already set.
     *
     * @return the icon size property
     */
    public final DoubleProperty sizeProperty() {
        if (size == null) {
            size = new SimpleStyleableDoubleProperty(
                    Styleables.SIZE,
                    this,
                    "size",
                    DEF_SIZE
            );
        }
        return size;
    }

    /**
     * Sets the size of the icon.
     *
     * @param size the new size to set
     */
    public final void setSize(double size) {
        sizeProperty().set(size);
    }

    /**
     * Returns the default icon representation.
     * Implementing classes must provide a default icon.
     *
     * @return the default icon key
     */
    public abstract String defaultIcon();

    /**
     * Returns the font family used for rendering the icon.
     * Implementing classes must specify the font family.
     *
     * @return the font family name as a {@link String}
     */
    public final String fontFamily() {
        return family;
    }

    /**
     * Returns a {@link ResourceBundle} containing icon names and their corresponding unicode values.
     * Implementing classes must provide the resource bundle containing the icon mappings.
     *
     * @return the {@link ResourceBundle} containing icon data
     */
    protected abstract ResourceBundle resource();

    /**
     * Returns a list of available icon names.
     * Implementing classes must provide the list of supported icons.
     *
     * @return a {@link List} of icon names
     */
    public final List<String> icons() {
        List<String> keys = Collections.list(resource().getKeys());
        Collections.sort(keys);  // Sorts the keys alphabetically
        return keys;
    }

    /**
     * Handles updates to the icon by retrieving its Unicode value from the provided map.
     * This method can be used to update the displayed icon based on its name.
     * @param name  the name of the icon to be set
     */
    private void handleIcon(String name) {
        String string = resource().getString(name.toUpperCase());
        if (string.isBlank())   {
            string= resource().getString(defaultIcon());
        }
        String[] parts = string.split("/");
        if (parts.length < 2){
            throw new RuntimeException("No valid Icon was found");
        }
        String part = parts[0];
        if (part.startsWith("U+")) {
            part = "0x"+part.replace("U+", "");
            part = new String(Character.toChars(Integer.decode(part)));
        }
        setText(part);
        family = parts[1];
        handleSize(getSize());
    }

    /**
     * Handles updates to the icon size.
     * This method can be used to apply size changes dynamically.
     *
     * @param size the new size of the icon
     */
    private void handleSize(double size) {
        if (family == null) return;
        Font f = new Font(family, size);
        setFont(f);
    }


    /**
     * A private static class containing CSS styleable properties for {@code AbstractIcon}.
     */
    @SuppressWarnings("all")
    private static final class Styleables {
        /**
         * The CSS metadata for the icon size.
         */
        public static final CssMetaData<AbstractIcon, Number> SIZE =
                new CssMetaData<>("-nfx-icon-size", StyleConverter.getSizeConverter(), DEF_SIZE) {
                    @Override
                    public boolean isSettable(AbstractIcon s) {
                        return s.size == null || !s.size.isBound();
                    }

                    @Override
                    public StyleableProperty<Number> getStyleableProperty(AbstractIcon s) {
                        return (StyleableProperty<Number>) s.sizeProperty();
                    }
                };
        /**
         * The CSS metadata for the icon.
         */
        public static final CssMetaData<AbstractIcon, String> ICON =
                new CssMetaData<AbstractIcon, String>("-nfx-icon-name", StyleConverter.getStringConverter()) {
                    @Override
                    public boolean isSettable(AbstractIcon s) {
                        return s.icon == null || !s.icon.isBound();
                    }

                    @Override
                    public StyleableProperty<String> getStyleableProperty(AbstractIcon s) {
                        return (StyleableProperty<String>) s.iconProperty();
                    }
                };

        /**
         * A list containing all CSS metadata properties for styleable attributes.
         */
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables = new ArrayList<>(Text.getClassCssMetaData());
            Collections.addAll(styleables, SIZE, ICON);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }

    }

    /**
     * Returns the list of CSS metadata for the class.
     *
     * @return a {@link List} of {@link CssMetaData} defining styleable properties
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return Styleables.STYLEABLES;
    }

    /**
     * Returns the list of CSS metadata applicable to this instance.
     *
     * @return a {@link List} of {@link CssMetaData} defining styleable properties
     */
    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }
}
