package pelea.marp.common.rp.reactive.enums;

import java.io.Serializable;

/**
 * Enumeration of allowed flag types
 * @author cguzman
 *
 */
public enum FILTER implements Serializable{
	NONE, BYFLUENTS_JUST_IN_EFFECTS, BYFLUENTS, BYVARIABLES, BYVARIABLES_SAVE_FLUENT
}
