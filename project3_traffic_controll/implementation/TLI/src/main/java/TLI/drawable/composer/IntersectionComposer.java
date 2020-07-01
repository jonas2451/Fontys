package TLI.drawable.composer;

import TLI.intersection.Intersection;
import TLI.position.Position;

public interface IntersectionComposer {
    Intersection createIntersection(Position position);
}
