package tannyjung.tanscomplexmagic.procedures;

import tannyjung.tanscomplexmagic.network.TanscomplexmagicModVariables;

import net.minecraft.world.entity.Entity;

public class GUIreturnPageLeftProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		String variable_text = "";
		String variable_text2 = "";
		if (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).book_page < 10) {
			variable_text = "00" + (new java.text.DecimalFormat("##.##").format(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).book_page)).replace(".0", "");
		} else if (entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).book_page < 100) {
			variable_text = "0" + (new java.text.DecimalFormat("##.##").format(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).book_page)).replace(".0", "");
		} else {
			variable_text = (new java.text.DecimalFormat("##.##").format(entity.getData(TanscomplexmagicModVariables.PLAYER_VARIABLES).book_page)).replace(".0", "");
		}
		if (true) {
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(0, 1)) == 0) {
				variable_text2 = "\u2070";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(0, 1)) == 1) {
				variable_text2 = "\u00B9";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(0, 1)) == 2) {
				variable_text2 = "\u00B2";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(0, 1)) == 3) {
				variable_text2 = "\u00B3";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(0, 1)) == 4) {
				variable_text2 = "\u2074";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(0, 1)) == 5) {
				variable_text2 = "\u2075";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(0, 1)) == 6) {
				variable_text2 = "\u2076";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(0, 1)) == 7) {
				variable_text2 = "\u2077";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(0, 1)) == 8) {
				variable_text2 = "\u2078";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(0, 1)) == 9) {
				variable_text2 = "\u2079";
			}
		}
		if (true) {
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(1, 2)) == 0) {
				variable_text2 = variable_text2 + "\u2070";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(1, 2)) == 1) {
				variable_text2 = variable_text2 + "\u00B9";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(1, 2)) == 2) {
				variable_text2 = variable_text2 + "\u00B2";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(1, 2)) == 3) {
				variable_text2 = variable_text2 + "\u00B3";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(1, 2)) == 4) {
				variable_text2 = variable_text2 + "\u2074";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(1, 2)) == 5) {
				variable_text2 = variable_text2 + "\u2075";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(1, 2)) == 6) {
				variable_text2 = variable_text2 + "\u2076";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(1, 2)) == 7) {
				variable_text2 = variable_text2 + "\u2077";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(1, 2)) == 8) {
				variable_text2 = variable_text2 + "\u2078";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(1, 2)) == 9) {
				variable_text2 = variable_text2 + "\u2079";
			}
		}
		if (true) {
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(2, 3)) == 0) {
				variable_text2 = variable_text2 + "\u2070";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(2, 3)) == 1) {
				variable_text2 = variable_text2 + "\u00B9";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(2, 3)) == 2) {
				variable_text2 = variable_text2 + "\u00B2";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(2, 3)) == 3) {
				variable_text2 = variable_text2 + "\u00B3";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(2, 3)) == 4) {
				variable_text2 = variable_text2 + "\u2074";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(2, 3)) == 5) {
				variable_text2 = variable_text2 + "\u2075";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(2, 3)) == 6) {
				variable_text2 = variable_text2 + "\u2076";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(2, 3)) == 7) {
				variable_text2 = variable_text2 + "\u2077";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(2, 3)) == 8) {
				variable_text2 = variable_text2 + "\u2078";
			}
			if (new Object() {
				double convert(String s) {
					try {
						return Double.parseDouble(s.trim());
					} catch (Exception e) {
					}
					return 0;
				}
			}.convert(variable_text.substring(2, 3)) == 9) {
				variable_text2 = variable_text2 + "\u2079";
			}
		}
		return variable_text2;
	}
}