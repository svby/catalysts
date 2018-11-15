package catalysts.training.trail

import catalysts.Vector2

fun polyArea(vertices: List<Vector2>): Double {
    var det = 0.0

    /** add product of x coordinate of ith point with y coordinate of (i + 1)th point **/

    for (i in 0 until vertices.size - 1)

        det += (vertices[i].x * vertices[i + 1].y)

    /** subtract product of y coordinate of ith point with x coordinate of (i + 1)th point **/

    for (i in 0 until vertices.size - 1)

        det -= (vertices[i].y * vertices[i + 1].x)


    /** find absolute value and divide by 2 **/

    det = Math.abs(det)

    det /= 2.0

    return det
}