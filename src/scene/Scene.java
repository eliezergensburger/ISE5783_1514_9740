package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * Scene class
 *
 * @author Israel Jacob & Avraham Meiri
 */
public class Scene {

    public final String name;
    public final Color background;
    public final AmbientLight ambientLight;
    public final Geometries geometries;

    /**
     * @param builder - The builder of Scene
     */
    private Scene(SceneBuilder builder) {
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
    }

    /**
     * inner class SceneBuilder builds Scene object using builder pattern
     */
    public static class SceneBuilder {

        private final String name;
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = new AmbientLight();
        private Geometries geometries = new Geometries();

        /**
         * This is the builder
         *
         * @param name The name of the scene
         */
        public SceneBuilder(String name) {
            this.name = name;
        }

        // chaining method

        /**
         * This function sets the background color of the scene and returns the scene builder.
         *
         * @param background The background color of the scene.
         * @return The SceneBuilder object.
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * > Sets the ambient light of the scene
         *
         * @param ambientLight The ambient light of the scene.
         * @return The SceneBuilder object.
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * This function sets the geometries of the scene
         *
         * @param geometries The geometries of the scene.
         * @return The SceneBuilder object.
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * This function returns the scene that we built.
         *
         * @return A new instance of the Scene class.
         */
        public Scene build() {
            return new Scene(this);
        }
    }
}