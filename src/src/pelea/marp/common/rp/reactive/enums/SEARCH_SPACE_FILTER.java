package pelea.marp.common.rp.reactive.enums;

import java.io.Serializable;

/**
 * Enumeration of allowed flag types
 * @author cguzman
 *
 */
public enum SEARCH_SPACE_FILTER implements Serializable{
	SELF_TREE, SELF_TREE_COORD_CURRENT_ROOT_AND_PLAN_NO_UPDATING, SELF_TREE_COORD_CURRENT_ROOT_AND_PLAN_UPDATING, OWN_ADAPTING_TREE, ADAPTING_TREE
}
