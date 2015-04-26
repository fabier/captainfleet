package trackr

import com.vividsolutions.jts.geom.Envelope

/**
 * Created by fabier on 16/04/15.
 */
class MapOptions {
    Envelope boundingBox
    List<MapMarkerLayer> mapMarkerLayers
}
