package sk.fiit.jim.agent.models;

import sk.fiit.robocup.library.geometry.Vector3D; //TODO treba porovnat s povodnym Vector3D u Hudeca

/**
 * Filters possible sudden changes in agent's position
 *
 *
 * @author Maros Urbanec
 */
public interface PositionChangeGuardian {
	public Vector3D decide(Vector3D oldPosition, Vector3D newPosition);
}