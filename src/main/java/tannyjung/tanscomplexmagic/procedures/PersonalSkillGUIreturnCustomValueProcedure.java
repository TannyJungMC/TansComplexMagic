package tannyjung.tanscomplexmagic.procedures;

import tannyjung.tanscomplexmagic.network.TanscomplexmagicModVariables;

import net.minecraft.world.entity.Entity;

public class PersonalSkillGUIreturnCustomValueProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		String variable_text = "";
		if ((entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_upgrade).equals("Custom")) {
			variable_text = (new java.text.DecimalFormat("##.##").format(new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom_value) * entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level)).replace(".0", "") + " > "
					+ (new java.text.DecimalFormat("##.##").format(new Object() {
						double convert(String s) {
							try {
								return Double.parseDouble(s.trim());
							} catch (Exception e) {
							}
							return 0;
						}
					}.convert(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom_value) * (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_level + 1))).replace(".0", "");
		} else {
			if ((entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom).equals("")) {
				variable_text = "";
			} else {
				variable_text = entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).personal_skill_custom_value;
			}
		}
		return variable_text;
	}
}