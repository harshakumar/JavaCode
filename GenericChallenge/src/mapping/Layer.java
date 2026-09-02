package mapping;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic layer of mappable elements.
 * T is bounded to Mappable, so a Layer can only ever hold things
 * that know how to render themselves on a map.
 */
public class Layer<T extends Mappable> {

    private List<T> layerElements = new ArrayList<>();

    public void addElement(T element) {
        layerElements.add(element);
    }

    public void addElements(List<T> elements) {
        layerElements.addAll(elements);
    }

    public void renderLayer() {
        for (T element : layerElements) {
            element.render();
        }
    }
}
