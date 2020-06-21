package org.jmisb.api.klv.st0601.dto;

/**
 * Data transfer object for Waypoint information.
 *
 * <p>This is used by Waypoint List (ST0601 Tag 141).
 */
public class Waypoint {
    private int waypointID;
    private short prosecutionOrder;
    private boolean manualMode;
    private boolean adhocSource;
    private Location location = new Location();

    /**
     * Get the waypoint identifier for this waypoint.
     *
     * <p>The Waypoint ID is a unique integer identifier for the Waypoint; the value is positive and
     * with each new waypoint the value increments by one.
     *
     * @return the waypoint identifier.
     */
    public int getWaypointID() {
        return waypointID;
    }

    /**
     * Set the waypoint identifier for this waypoint.
     *
     * <p>The Waypoint ID is a unique integer identifier for the Waypoint; the value is positive and
     * with each new waypoint the value increments by one.
     *
     * @param waypointID the waypoint identifier
     */
    public void setWaypointID(int waypointID) {
        this.waypointID = waypointID;
    }

    /**
     * Get the prosecution order.
     *
     * <p>The Prosecution Order value is the position in the order of operation list. Planned
     * waypoints are positive (i.e., &gt;0) values. The current waypoint has a value of zero.
     * Historical waypoints have negative values (i.e., &lt;0) in decreasing order. (i.e., each
     * completed waypoint has its Prosecution Order set to the next largest magnitude negative
     * number). To determine the last waypoint, take the min value of all Prosecution Orders.
     * Historical waypoints become static records, requiring updates only once every 30 seconds if
     * retained. When canceling a waypoint set its Prosecution Order to the maximum positive value
     * to indicate cancellation; this is the only value where multiple Waypoint Records can use the
     * same Prosecution Order.
     *
     * @return the prosecution order.
     */
    public short getProsecutionOrder() {
        return prosecutionOrder;
    }

    /**
     * Set the prosecution order.
     *
     * <p>The Prosecution Order value is the position in the order of operation list. Planned
     * waypoints are positive (i.e., &gt;0) values. The current waypoint has a value of zero.
     * Historical waypoints have negative values (i.e., &lt;0) in decreasing order. (i.e., each
     * completed waypoint has its Prosecution Order set to the next largest magnitude negative
     * number). To determine the last waypoint, take the min value of all Prosecution Orders.
     * Historical waypoints become static records, requiring updates only once every 30 seconds if
     * retained. When canceling a waypoint set its Prosecution Order to the maximum positive value
     * to indicate cancellation; this is the only value where multiple Waypoint Records can use the
     * same Prosecution Order.
     *
     * @param prosecutionOrder the prosecution order value.
     */
    public void setProsecutionOrder(short prosecutionOrder) {
        this.prosecutionOrder = prosecutionOrder;
    }

    /**
     * Get whether this waypoint is manual mode.
     *
     * @return true if the waypoint mode is manual, false if the waypoint is automatic
     */
    public boolean isManualMode() {
        return manualMode;
    }

    /**
     * Set whether this waypoint is manual mode.
     *
     * @param manualMode true if the waypoint mode is manual, false if the waypoint is automatic.
     */
    public void setManualMode(boolean manualMode) {
        this.manualMode = manualMode;
    }

    /**
     * Get whether this waypoint has an ad-hoc source.
     *
     * @return true if the waypoint is ad-hoc, false if the waypoint is pre-planned.
     */
    public boolean isAdhocSource() {
        return adhocSource;
    }

    /**
     * Set whether this waypoint has an ad-hoc source.
     *
     * @param adhocSource true if the waypoint is ad-hoc, false if the waypoint is pre-planned.
     */
    public void setAdhocSource(boolean adhocSource) {
        this.adhocSource = adhocSource;
    }

    /**
     * Get the waypoint location.
     *
     * @return the waypoint location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the waypoint location.
     *
     * @param location the waypoint location.
     */
    public void setLocation(Location location) {
        this.location = location;
    }
}
