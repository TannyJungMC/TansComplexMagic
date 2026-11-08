package tannyjung.tanscomplexmagic.network;

import tannyjung.tanscomplexmagic.TanscomplexmagicMod;

import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.HolderLookup;

import java.util.function.Supplier;

@EventBusSubscriber
public class TanscomplexmagicModVariables {
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, TanscomplexmagicMod.MODID);
	public static final Supplier<AttachmentType<PlayerVariables>> PLAYER_VARIABLES = ATTACHMENT_TYPES.register("player_variables", () -> AttachmentType.serializable(() -> new PlayerVariables()).build());

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		TanscomplexmagicMod.addNetworkMessage(PlayerVariablesSyncMessage.TYPE, PlayerVariablesSyncMessage.STREAM_CODEC, PlayerVariablesSyncMessage::handleData);
	}

	@SubscribeEvent
	public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
		if (event.getEntity() instanceof ServerPlayer player)
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
	}

	@SubscribeEvent
	public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
		if (event.getEntity() instanceof ServerPlayer player)
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
	}

	@SubscribeEvent
	public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (event.getEntity() instanceof ServerPlayer player)
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
	}

	@SubscribeEvent
	public static void onPlayerTickUpdateSyncPlayerVariables(PlayerTickEvent.Post event) {
		if (event.getEntity() instanceof ServerPlayer player && player.getData(PLAYER_VARIABLES)._syncDirty) {
			PacketDistributor.sendToPlayer(player, new PlayerVariablesSyncMessage(player.getData(PLAYER_VARIABLES)));
			player.getData(PLAYER_VARIABLES)._syncDirty = false;
		}
	}

	@SubscribeEvent
	public static void clonePlayer(PlayerEvent.Clone event) {
		PlayerVariables original = event.getOriginal().getData(PLAYER_VARIABLES);
		PlayerVariables clone = new PlayerVariables();
		clone.agreement_accept = original.agreement_accept;
		clone.ally_list_auto1 = original.ally_list_auto1;
		clone.ally_list_auto2 = original.ally_list_auto2;
		clone.ally_list_auto3 = original.ally_list_auto3;
		clone.ally_list_chat_setting = original.ally_list_chat_setting;
		clone.ally_list_manual = original.ally_list_manual;
		clone.alternative_keyboard = original.alternative_keyboard;
		clone.alternative_keyboard_save = original.alternative_keyboard_save;
		clone.alternative_keyboard_save2 = original.alternative_keyboard_save2;
		clone.alternative_keyboard_save3 = original.alternative_keyboard_save3;
		clone.alternative_keyboard_save4 = original.alternative_keyboard_save4;
		clone.alternative_keyboard_save5 = original.alternative_keyboard_save5;
		clone.alternative_keyboard_save6 = original.alternative_keyboard_save6;
		clone.book_compass_custom_posX = original.book_compass_custom_posX;
		clone.book_compass_custom_posZ = original.book_compass_custom_posZ;
		clone.book_log_display_info = original.book_log_display_info;
		clone.book_log_display_info_duration = original.book_log_display_info_duration;
		clone.book_log_display_notification = original.book_log_display_notification;
		clone.book_log_display_notification_duraation = original.book_log_display_notification_duraation;
		clone.book_log_display_notification_duration = original.book_log_display_notification_duration;
		clone.book_log_type = original.book_log_type;
		clone.book_page = original.book_page;
		clone.book_shortcut_custom = original.book_shortcut_custom;
		clone.id = original.id;
		clone.main_key = original.main_key;
		clone.mana = original.mana;
		clone.mana_charge = original.mana_charge;
		clone.mana_max = original.mana_max;
		clone.personal_skill = original.personal_skill;
		clone.personal_skill_cooldown = original.personal_skill_cooldown;
		clone.personal_skill_custom = original.personal_skill_custom;
		clone.personal_skill_custom_value = original.personal_skill_custom_value;
		clone.personal_skill_duration = original.personal_skill_duration;
		clone.personal_skill_level = original.personal_skill_level;
		clone.personal_skill_loop_second = original.personal_skill_loop_second;
		clone.personal_skill_loop_tick = original.personal_skill_loop_tick;
		clone.personal_skill_radius = original.personal_skill_radius;
		clone.personal_skill_skill_blink = original.personal_skill_skill_blink;
		clone.personal_skill_skill_circle_of_healing = original.personal_skill_skill_circle_of_healing;
		clone.personal_skill_skill_rejuvenating_mushroom = original.personal_skill_skill_rejuvenating_mushroom;
		clone.personal_skill_skill_the_missing_person = original.personal_skill_skill_the_missing_person;
		clone.personal_skill_upgrade = original.personal_skill_upgrade;
		clone.personal_weapon = original.personal_weapon;
		clone.personal_weapon_flying = original.personal_weapon_flying;
		clone.personal_weapon_flying_tick = original.personal_weapon_flying_tick;
		clone.personal_weapon_place = original.personal_weapon_place;
		clone.personal_weapon_pos_x = original.personal_weapon_pos_x;
		clone.personal_weapon_pos_y = original.personal_weapon_pos_y;
		clone.personal_weapon_pos_z = original.personal_weapon_pos_z;
		clone.personal_weapon_pose_x = original.personal_weapon_pose_x;
		clone.personal_weapon_pose_y = original.personal_weapon_pose_y;
		clone.personal_weapon_pose_z = original.personal_weapon_pose_z;
		clone.personal_weapon_rotation = original.personal_weapon_rotation;
		clone.personal_weapon_rotation_reverse = original.personal_weapon_rotation_reverse;
		clone.personal_weapon_show_center = original.personal_weapon_show_center;
		clone.player_tick_second = original.player_tick_second;
		clone.player_tick_tick = original.player_tick_tick;
		clone.spell = original.spell;
		clone.spell4_ability = original.spell4_ability;
		clone.spell4_ability_cooldown = original.spell4_ability_cooldown;
		clone.spell4_ability_duration = original.spell4_ability_duration;
		clone.spell4_auto_shot_cooldown = original.spell4_auto_shot_cooldown;
		clone.spell4_can_summon = original.spell4_can_summon;
		clone.spell4_constant = original.spell4_constant;
		clone.spell4_distance = original.spell4_distance;
		clone.spell4_distance_set = original.spell4_distance_set;
		clone.spell4_distancing_in = original.spell4_distancing_in;
		clone.spell4_distancing_out = original.spell4_distancing_out;
		clone.spell4_distancing_speed = original.spell4_distancing_speed;
		clone.spell4_guard_pose = original.spell4_guard_pose;
		clone.spell4_guard_pose_distance = original.spell4_guard_pose_distance;
		clone.spell4_homing = original.spell4_homing;
		clone.spell4_lock_reverse = original.spell4_lock_reverse;
		clone.spell4_lock_shoot = original.spell4_lock_shoot;
		clone.spell4_lock_target = original.spell4_lock_target;
		clone.spell4_mode = original.spell4_mode;
		clone.spell4_pause_at_mark = original.spell4_pause_at_mark;
		clone.spell4_pause_balancing = original.spell4_pause_balancing;
		clone.spell4_pause_boundary = original.spell4_pause_boundary;
		clone.spell4_pause_gravity = original.spell4_pause_gravity;
		clone.spell4_pause_rotation = original.spell4_pause_rotation;
		clone.spell4_reverse = original.spell4_reverse;
		clone.spell4_shoot = original.spell4_shoot;
		clone.spell4_show_overlay = original.spell4_show_overlay;
		clone.spell4_show_target_arrow = original.spell4_show_target_arrow;
		clone.spell4_skill_sword_skill_hidden_objects = original.spell4_skill_sword_skill_hidden_objects;
		clone.spell4_tick_auto_shot = original.spell4_tick_auto_shot;
		clone.spell4_tick_fly_updown = original.spell4_tick_fly_updown;
		clone.spell4_tick_summon = original.spell4_tick_summon;
		clone.spell4_type = original.spell4_type;
		clone.spell4_type_tag = original.spell4_type_tag;
		clone.spell4_weapon_anchor_count = original.spell4_weapon_anchor_count;
		clone.spell4_weapon_anchor_count_max = original.spell4_weapon_anchor_count_max;
		clone.spell4_weapon_anchor_level = original.spell4_weapon_anchor_level;
		clone.spell4_weapon_bow_count = original.spell4_weapon_bow_count;
		clone.spell4_weapon_bow_count_max = original.spell4_weapon_bow_count_max;
		clone.spell4_weapon_bow_level = original.spell4_weapon_bow_level;
		clone.spell4_weapon_knight_sword_count = original.spell4_weapon_knight_sword_count;
		clone.spell4_weapon_knight_sword_count_max = original.spell4_weapon_knight_sword_count_max;
		clone.spell4_weapon_lantern_count = original.spell4_weapon_lantern_count;
		clone.spell4_weapon_lantern_count_max = original.spell4_weapon_lantern_count_max;
		clone.spell4_weapon_lantern_level = original.spell4_weapon_lantern_level;
		clone.spell4_weapon_shield_count = original.spell4_weapon_shield_count;
		clone.spell4_weapon_shield_count_max = original.spell4_weapon_shield_count_max;
		clone.spell4_weapon_shield_level = original.spell4_weapon_shield_level;
		clone.spell4_weapon_sword_count = original.spell4_weapon_sword_count;
		clone.spell4_weapon_sword_count_max = original.spell4_weapon_sword_count_max;
		clone.spell4_weapon_sword_level = original.spell4_weapon_sword_level;
		clone.tick = original.tick;
		clone.user_book_flying_tick = original.user_book_flying_tick;
		clone.user_book_item_open = original.user_book_item_open;
		clone.user_book_place = original.user_book_place;
		clone.user_book_posX = original.user_book_posX;
		clone.user_book_posY = original.user_book_posY;
		clone.user_book_posZ = original.user_book_posZ;
		clone.user_DELETE_THIS = original.user_DELETE_THIS;
		clone.user = original.user;
		clone.spell4_ability_cooldown_max = original.spell4_ability_cooldown_max;
		clone.spell4_ability_duration_max = original.spell4_ability_duration_max;
		if (!event.isWasDeath()) {
		}
		event.getEntity().setData(PLAYER_VARIABLES, clone);
	}

	public static class PlayerVariables implements INBTSerializable<CompoundTag> {
		boolean _syncDirty = false;
		public boolean agreement_accept = false;
		public String ally_list_auto1 = "";
		public String ally_list_auto2 = "";
		public String ally_list_auto3 = "";
		public String ally_list_chat_setting = "";
		public String ally_list_manual = "";
		public String alternative_keyboard = "";
		public String alternative_keyboard_save = "";
		public String alternative_keyboard_save2 = "";
		public String alternative_keyboard_save3 = "";
		public String alternative_keyboard_save4 = "";
		public String alternative_keyboard_save5 = "";
		public String alternative_keyboard_save6 = "";
		public double book_compass_custom_posX = 0;
		public double book_compass_custom_posZ = 0;
		public String book_log_display_info = "";
		public double book_log_display_info_duration = 0;
		public String book_log_display_notification = "";
		public String book_log_display_notification_duraation = "";
		public double book_log_display_notification_duration = 0;
		public String book_log_type = "";
		public double book_page = 0.0;
		public double book_shortcut_custom = 0;
		public String id = "";
		public boolean main_key = false;
		public double mana = 0;
		public double mana_charge = 0;
		public double mana_max = 0;
		public String personal_skill = "";
		public double personal_skill_cooldown = 0;
		public String personal_skill_custom = "";
		public String personal_skill_custom_value = "";
		public double personal_skill_duration = 0;
		public double personal_skill_level = 0;
		public boolean personal_skill_loop_second = false;
		public boolean personal_skill_loop_tick = false;
		public double personal_skill_radius = 0;
		public boolean personal_skill_skill_blink = false;
		public boolean personal_skill_skill_circle_of_healing = false;
		public boolean personal_skill_skill_rejuvenating_mushroom = false;
		public boolean personal_skill_skill_the_missing_person = false;
		public String personal_skill_upgrade = "";
		public ItemStack personal_weapon = ItemStack.EMPTY;
		public boolean personal_weapon_flying = false;
		public double personal_weapon_flying_tick = 0;
		public boolean personal_weapon_place = false;
		public double personal_weapon_pos_x = 0;
		public double personal_weapon_pos_y = 0;
		public double personal_weapon_pos_z = 0;
		public double personal_weapon_pose_x = 0;
		public double personal_weapon_pose_y = 0;
		public double personal_weapon_pose_z = 0;
		public boolean personal_weapon_rotation = false;
		public boolean personal_weapon_rotation_reverse = false;
		public boolean personal_weapon_show_center = false;
		public double player_tick_second = 0;
		public double player_tick_tick = 0;
		public double spell = 0;
		public String spell4_ability = "";
		public double spell4_ability_cooldown = 0;
		public double spell4_ability_duration = 0;
		public double spell4_auto_shot_cooldown = 0;
		public boolean spell4_can_summon = false;
		public boolean spell4_constant = false;
		public double spell4_distance = 0;
		public double spell4_distance_set = 0;
		public double spell4_distancing_in = 0;
		public double spell4_distancing_out = 0;
		public double spell4_distancing_speed = 0;
		public boolean spell4_guard_pose = false;
		public double spell4_guard_pose_distance = 0;
		public boolean spell4_homing = false;
		public boolean spell4_lock_reverse = false;
		public boolean spell4_lock_shoot = false;
		public boolean spell4_lock_target = false;
		public String spell4_mode = "";
		public boolean spell4_pause_at_mark = false;
		public boolean spell4_pause_balancing = false;
		public double spell4_pause_boundary = 0;
		public boolean spell4_pause_gravity = false;
		public double spell4_pause_rotation = 0;
		public boolean spell4_reverse = false;
		public boolean spell4_shoot = false;
		public boolean spell4_show_overlay = false;
		public boolean spell4_show_target_arrow = false;
		public boolean spell4_skill_sword_skill_hidden_objects = false;
		public double spell4_tick_auto_shot = 0;
		public double spell4_tick_fly_updown = 0;
		public double spell4_tick_summon = 0;
		public String spell4_type = "";
		public String spell4_type_tag = "";
		public double spell4_weapon_anchor_count = 0;
		public double spell4_weapon_anchor_count_max = 0;
		public double spell4_weapon_anchor_level = 0;
		public double spell4_weapon_bow_count = 0;
		public double spell4_weapon_bow_count_max = 0;
		public double spell4_weapon_bow_level = 0;
		public double spell4_weapon_knight_sword_count = 0;
		public double spell4_weapon_knight_sword_count_max = 0;
		public double spell4_weapon_lantern_count = 0;
		public double spell4_weapon_lantern_count_max = 0;
		public double spell4_weapon_lantern_level = 0;
		public double spell4_weapon_shield_count = 0;
		public double spell4_weapon_shield_count_max = 0;
		public double spell4_weapon_shield_level = 0;
		public double spell4_weapon_sword_count = 0;
		public double spell4_weapon_sword_count_max = 0;
		public double spell4_weapon_sword_level = 0;
		public double tick = 0;
		public double user_book_flying_tick = 0;
		public boolean user_book_item_open = false;
		public boolean user_book_place = false;
		public double user_book_posX = 0;
		public double user_book_posY = 0.0;
		public double user_book_posZ = 0;
		public String user_DELETE_THIS = "";
		public boolean user = false;
		public double spell4_ability_cooldown_max = 0;
		public double spell4_ability_duration_max = 0;

		@Override
		public CompoundTag serializeNBT(HolderLookup.Provider lookupProvider) {
			CompoundTag nbt = new CompoundTag();
			nbt.putBoolean("agreement_accept", agreement_accept);
			nbt.putString("ally_list_auto1", ally_list_auto1);
			nbt.putString("ally_list_auto2", ally_list_auto2);
			nbt.putString("ally_list_auto3", ally_list_auto3);
			nbt.putString("ally_list_chat_setting", ally_list_chat_setting);
			nbt.putString("ally_list_manual", ally_list_manual);
			nbt.putString("alternative_keyboard", alternative_keyboard);
			nbt.putString("alternative_keyboard_save", alternative_keyboard_save);
			nbt.putString("alternative_keyboard_save2", alternative_keyboard_save2);
			nbt.putString("alternative_keyboard_save3", alternative_keyboard_save3);
			nbt.putString("alternative_keyboard_save4", alternative_keyboard_save4);
			nbt.putString("alternative_keyboard_save5", alternative_keyboard_save5);
			nbt.putString("alternative_keyboard_save6", alternative_keyboard_save6);
			nbt.putDouble("book_compass_custom_posX", book_compass_custom_posX);
			nbt.putDouble("book_compass_custom_posZ", book_compass_custom_posZ);
			nbt.putString("book_log_display_info", book_log_display_info);
			nbt.putDouble("book_log_display_info_duration", book_log_display_info_duration);
			nbt.putString("book_log_display_notification", book_log_display_notification);
			nbt.putString("book_log_display_notification_duraation", book_log_display_notification_duraation);
			nbt.putDouble("book_log_display_notification_duration", book_log_display_notification_duration);
			nbt.putString("book_log_type", book_log_type);
			nbt.putDouble("book_page", book_page);
			nbt.putDouble("book_shortcut_custom", book_shortcut_custom);
			nbt.putString("id", id);
			nbt.putBoolean("main_key", main_key);
			nbt.putDouble("mana", mana);
			nbt.putDouble("mana_charge", mana_charge);
			nbt.putDouble("mana_max", mana_max);
			nbt.putString("personal_skill", personal_skill);
			nbt.putDouble("personal_skill_cooldown", personal_skill_cooldown);
			nbt.putString("personal_skill_custom", personal_skill_custom);
			nbt.putString("personal_skill_custom_value", personal_skill_custom_value);
			nbt.putDouble("personal_skill_duration", personal_skill_duration);
			nbt.putDouble("personal_skill_level", personal_skill_level);
			nbt.putBoolean("personal_skill_loop_second", personal_skill_loop_second);
			nbt.putBoolean("personal_skill_loop_tick", personal_skill_loop_tick);
			nbt.putDouble("personal_skill_radius", personal_skill_radius);
			nbt.putBoolean("personal_skill_skill_blink", personal_skill_skill_blink);
			nbt.putBoolean("personal_skill_skill_circle_of_healing", personal_skill_skill_circle_of_healing);
			nbt.putBoolean("personal_skill_skill_rejuvenating_mushroom", personal_skill_skill_rejuvenating_mushroom);
			nbt.putBoolean("personal_skill_skill_the_missing_person", personal_skill_skill_the_missing_person);
			nbt.putString("personal_skill_upgrade", personal_skill_upgrade);
			nbt.put("personal_weapon", personal_weapon.saveOptional(lookupProvider));
			nbt.putBoolean("personal_weapon_flying", personal_weapon_flying);
			nbt.putDouble("personal_weapon_flying_tick", personal_weapon_flying_tick);
			nbt.putBoolean("personal_weapon_place", personal_weapon_place);
			nbt.putDouble("personal_weapon_pos_x", personal_weapon_pos_x);
			nbt.putDouble("personal_weapon_pos_y", personal_weapon_pos_y);
			nbt.putDouble("personal_weapon_pos_z", personal_weapon_pos_z);
			nbt.putDouble("personal_weapon_pose_x", personal_weapon_pose_x);
			nbt.putDouble("personal_weapon_pose_y", personal_weapon_pose_y);
			nbt.putDouble("personal_weapon_pose_z", personal_weapon_pose_z);
			nbt.putBoolean("personal_weapon_rotation", personal_weapon_rotation);
			nbt.putBoolean("personal_weapon_rotation_reverse", personal_weapon_rotation_reverse);
			nbt.putBoolean("personal_weapon_show_center", personal_weapon_show_center);
			nbt.putDouble("player_tick_second", player_tick_second);
			nbt.putDouble("player_tick_tick", player_tick_tick);
			nbt.putDouble("spell", spell);
			nbt.putString("spell4_ability", spell4_ability);
			nbt.putDouble("spell4_ability_cooldown", spell4_ability_cooldown);
			nbt.putDouble("spell4_ability_duration", spell4_ability_duration);
			nbt.putDouble("spell4_auto_shot_cooldown", spell4_auto_shot_cooldown);
			nbt.putBoolean("spell4_can_summon", spell4_can_summon);
			nbt.putBoolean("spell4_constant", spell4_constant);
			nbt.putDouble("spell4_distance", spell4_distance);
			nbt.putDouble("spell4_distance_set", spell4_distance_set);
			nbt.putDouble("spell4_distancing_in", spell4_distancing_in);
			nbt.putDouble("spell4_distancing_out", spell4_distancing_out);
			nbt.putDouble("spell4_distancing_speed", spell4_distancing_speed);
			nbt.putBoolean("spell4_guard_pose", spell4_guard_pose);
			nbt.putDouble("spell4_guard_pose_distance", spell4_guard_pose_distance);
			nbt.putBoolean("spell4_homing", spell4_homing);
			nbt.putBoolean("spell4_lock_reverse", spell4_lock_reverse);
			nbt.putBoolean("spell4_lock_shoot", spell4_lock_shoot);
			nbt.putBoolean("spell4_lock_target", spell4_lock_target);
			nbt.putString("spell4_mode", spell4_mode);
			nbt.putBoolean("spell4_pause_at_mark", spell4_pause_at_mark);
			nbt.putBoolean("spell4_pause_balancing", spell4_pause_balancing);
			nbt.putDouble("spell4_pause_boundary", spell4_pause_boundary);
			nbt.putBoolean("spell4_pause_gravity", spell4_pause_gravity);
			nbt.putDouble("spell4_pause_rotation", spell4_pause_rotation);
			nbt.putBoolean("spell4_reverse", spell4_reverse);
			nbt.putBoolean("spell4_shoot", spell4_shoot);
			nbt.putBoolean("spell4_show_overlay", spell4_show_overlay);
			nbt.putBoolean("spell4_show_target_arrow", spell4_show_target_arrow);
			nbt.putBoolean("spell4_skill_sword_skill_hidden_objects", spell4_skill_sword_skill_hidden_objects);
			nbt.putDouble("spell4_tick_auto_shot", spell4_tick_auto_shot);
			nbt.putDouble("spell4_tick_fly_updown", spell4_tick_fly_updown);
			nbt.putDouble("spell4_tick_summon", spell4_tick_summon);
			nbt.putString("spell4_type", spell4_type);
			nbt.putString("spell4_type_tag", spell4_type_tag);
			nbt.putDouble("spell4_weapon_anchor_count", spell4_weapon_anchor_count);
			nbt.putDouble("spell4_weapon_anchor_count_max", spell4_weapon_anchor_count_max);
			nbt.putDouble("spell4_weapon_anchor_level", spell4_weapon_anchor_level);
			nbt.putDouble("spell4_weapon_bow_count", spell4_weapon_bow_count);
			nbt.putDouble("spell4_weapon_bow_count_max", spell4_weapon_bow_count_max);
			nbt.putDouble("spell4_weapon_bow_level", spell4_weapon_bow_level);
			nbt.putDouble("spell4_weapon_knight_sword_count", spell4_weapon_knight_sword_count);
			nbt.putDouble("spell4_weapon_knight_sword_count_max", spell4_weapon_knight_sword_count_max);
			nbt.putDouble("spell4_weapon_lantern_count", spell4_weapon_lantern_count);
			nbt.putDouble("spell4_weapon_lantern_count_max", spell4_weapon_lantern_count_max);
			nbt.putDouble("spell4_weapon_lantern_level", spell4_weapon_lantern_level);
			nbt.putDouble("spell4_weapon_shield_count", spell4_weapon_shield_count);
			nbt.putDouble("spell4_weapon_shield_count_max", spell4_weapon_shield_count_max);
			nbt.putDouble("spell4_weapon_shield_level", spell4_weapon_shield_level);
			nbt.putDouble("spell4_weapon_sword_count", spell4_weapon_sword_count);
			nbt.putDouble("spell4_weapon_sword_count_max", spell4_weapon_sword_count_max);
			nbt.putDouble("spell4_weapon_sword_level", spell4_weapon_sword_level);
			nbt.putDouble("tick", tick);
			nbt.putDouble("user_book_flying_tick", user_book_flying_tick);
			nbt.putBoolean("user_book_item_open", user_book_item_open);
			nbt.putBoolean("user_book_place", user_book_place);
			nbt.putDouble("user_book_posX", user_book_posX);
			nbt.putDouble("user_book_posY", user_book_posY);
			nbt.putDouble("user_book_posZ", user_book_posZ);
			nbt.putString("user_DELETE_THIS", user_DELETE_THIS);
			nbt.putBoolean("user", user);
			nbt.putDouble("spell4_ability_cooldown_max", spell4_ability_cooldown_max);
			nbt.putDouble("spell4_ability_duration_max", spell4_ability_duration_max);
			return nbt;
		}

		@Override
		public void deserializeNBT(HolderLookup.Provider lookupProvider, CompoundTag nbt) {
			agreement_accept = nbt.getBoolean("agreement_accept");
			ally_list_auto1 = nbt.getString("ally_list_auto1");
			ally_list_auto2 = nbt.getString("ally_list_auto2");
			ally_list_auto3 = nbt.getString("ally_list_auto3");
			ally_list_chat_setting = nbt.getString("ally_list_chat_setting");
			ally_list_manual = nbt.getString("ally_list_manual");
			alternative_keyboard = nbt.getString("alternative_keyboard");
			alternative_keyboard_save = nbt.getString("alternative_keyboard_save");
			alternative_keyboard_save2 = nbt.getString("alternative_keyboard_save2");
			alternative_keyboard_save3 = nbt.getString("alternative_keyboard_save3");
			alternative_keyboard_save4 = nbt.getString("alternative_keyboard_save4");
			alternative_keyboard_save5 = nbt.getString("alternative_keyboard_save5");
			alternative_keyboard_save6 = nbt.getString("alternative_keyboard_save6");
			book_compass_custom_posX = nbt.getDouble("book_compass_custom_posX");
			book_compass_custom_posZ = nbt.getDouble("book_compass_custom_posZ");
			book_log_display_info = nbt.getString("book_log_display_info");
			book_log_display_info_duration = nbt.getDouble("book_log_display_info_duration");
			book_log_display_notification = nbt.getString("book_log_display_notification");
			book_log_display_notification_duraation = nbt.getString("book_log_display_notification_duraation");
			book_log_display_notification_duration = nbt.getDouble("book_log_display_notification_duration");
			book_log_type = nbt.getString("book_log_type");
			book_page = nbt.getDouble("book_page");
			book_shortcut_custom = nbt.getDouble("book_shortcut_custom");
			id = nbt.getString("id");
			main_key = nbt.getBoolean("main_key");
			mana = nbt.getDouble("mana");
			mana_charge = nbt.getDouble("mana_charge");
			mana_max = nbt.getDouble("mana_max");
			personal_skill = nbt.getString("personal_skill");
			personal_skill_cooldown = nbt.getDouble("personal_skill_cooldown");
			personal_skill_custom = nbt.getString("personal_skill_custom");
			personal_skill_custom_value = nbt.getString("personal_skill_custom_value");
			personal_skill_duration = nbt.getDouble("personal_skill_duration");
			personal_skill_level = nbt.getDouble("personal_skill_level");
			personal_skill_loop_second = nbt.getBoolean("personal_skill_loop_second");
			personal_skill_loop_tick = nbt.getBoolean("personal_skill_loop_tick");
			personal_skill_radius = nbt.getDouble("personal_skill_radius");
			personal_skill_skill_blink = nbt.getBoolean("personal_skill_skill_blink");
			personal_skill_skill_circle_of_healing = nbt.getBoolean("personal_skill_skill_circle_of_healing");
			personal_skill_skill_rejuvenating_mushroom = nbt.getBoolean("personal_skill_skill_rejuvenating_mushroom");
			personal_skill_skill_the_missing_person = nbt.getBoolean("personal_skill_skill_the_missing_person");
			personal_skill_upgrade = nbt.getString("personal_skill_upgrade");
			personal_weapon = ItemStack.parseOptional(lookupProvider, nbt.getCompound("personal_weapon"));
			personal_weapon_flying = nbt.getBoolean("personal_weapon_flying");
			personal_weapon_flying_tick = nbt.getDouble("personal_weapon_flying_tick");
			personal_weapon_place = nbt.getBoolean("personal_weapon_place");
			personal_weapon_pos_x = nbt.getDouble("personal_weapon_pos_x");
			personal_weapon_pos_y = nbt.getDouble("personal_weapon_pos_y");
			personal_weapon_pos_z = nbt.getDouble("personal_weapon_pos_z");
			personal_weapon_pose_x = nbt.getDouble("personal_weapon_pose_x");
			personal_weapon_pose_y = nbt.getDouble("personal_weapon_pose_y");
			personal_weapon_pose_z = nbt.getDouble("personal_weapon_pose_z");
			personal_weapon_rotation = nbt.getBoolean("personal_weapon_rotation");
			personal_weapon_rotation_reverse = nbt.getBoolean("personal_weapon_rotation_reverse");
			personal_weapon_show_center = nbt.getBoolean("personal_weapon_show_center");
			player_tick_second = nbt.getDouble("player_tick_second");
			player_tick_tick = nbt.getDouble("player_tick_tick");
			spell = nbt.getDouble("spell");
			spell4_ability = nbt.getString("spell4_ability");
			spell4_ability_cooldown = nbt.getDouble("spell4_ability_cooldown");
			spell4_ability_duration = nbt.getDouble("spell4_ability_duration");
			spell4_auto_shot_cooldown = nbt.getDouble("spell4_auto_shot_cooldown");
			spell4_can_summon = nbt.getBoolean("spell4_can_summon");
			spell4_constant = nbt.getBoolean("spell4_constant");
			spell4_distance = nbt.getDouble("spell4_distance");
			spell4_distance_set = nbt.getDouble("spell4_distance_set");
			spell4_distancing_in = nbt.getDouble("spell4_distancing_in");
			spell4_distancing_out = nbt.getDouble("spell4_distancing_out");
			spell4_distancing_speed = nbt.getDouble("spell4_distancing_speed");
			spell4_guard_pose = nbt.getBoolean("spell4_guard_pose");
			spell4_guard_pose_distance = nbt.getDouble("spell4_guard_pose_distance");
			spell4_homing = nbt.getBoolean("spell4_homing");
			spell4_lock_reverse = nbt.getBoolean("spell4_lock_reverse");
			spell4_lock_shoot = nbt.getBoolean("spell4_lock_shoot");
			spell4_lock_target = nbt.getBoolean("spell4_lock_target");
			spell4_mode = nbt.getString("spell4_mode");
			spell4_pause_at_mark = nbt.getBoolean("spell4_pause_at_mark");
			spell4_pause_balancing = nbt.getBoolean("spell4_pause_balancing");
			spell4_pause_boundary = nbt.getDouble("spell4_pause_boundary");
			spell4_pause_gravity = nbt.getBoolean("spell4_pause_gravity");
			spell4_pause_rotation = nbt.getDouble("spell4_pause_rotation");
			spell4_reverse = nbt.getBoolean("spell4_reverse");
			spell4_shoot = nbt.getBoolean("spell4_shoot");
			spell4_show_overlay = nbt.getBoolean("spell4_show_overlay");
			spell4_show_target_arrow = nbt.getBoolean("spell4_show_target_arrow");
			spell4_skill_sword_skill_hidden_objects = nbt.getBoolean("spell4_skill_sword_skill_hidden_objects");
			spell4_tick_auto_shot = nbt.getDouble("spell4_tick_auto_shot");
			spell4_tick_fly_updown = nbt.getDouble("spell4_tick_fly_updown");
			spell4_tick_summon = nbt.getDouble("spell4_tick_summon");
			spell4_type = nbt.getString("spell4_type");
			spell4_type_tag = nbt.getString("spell4_type_tag");
			spell4_weapon_anchor_count = nbt.getDouble("spell4_weapon_anchor_count");
			spell4_weapon_anchor_count_max = nbt.getDouble("spell4_weapon_anchor_count_max");
			spell4_weapon_anchor_level = nbt.getDouble("spell4_weapon_anchor_level");
			spell4_weapon_bow_count = nbt.getDouble("spell4_weapon_bow_count");
			spell4_weapon_bow_count_max = nbt.getDouble("spell4_weapon_bow_count_max");
			spell4_weapon_bow_level = nbt.getDouble("spell4_weapon_bow_level");
			spell4_weapon_knight_sword_count = nbt.getDouble("spell4_weapon_knight_sword_count");
			spell4_weapon_knight_sword_count_max = nbt.getDouble("spell4_weapon_knight_sword_count_max");
			spell4_weapon_lantern_count = nbt.getDouble("spell4_weapon_lantern_count");
			spell4_weapon_lantern_count_max = nbt.getDouble("spell4_weapon_lantern_count_max");
			spell4_weapon_lantern_level = nbt.getDouble("spell4_weapon_lantern_level");
			spell4_weapon_shield_count = nbt.getDouble("spell4_weapon_shield_count");
			spell4_weapon_shield_count_max = nbt.getDouble("spell4_weapon_shield_count_max");
			spell4_weapon_shield_level = nbt.getDouble("spell4_weapon_shield_level");
			spell4_weapon_sword_count = nbt.getDouble("spell4_weapon_sword_count");
			spell4_weapon_sword_count_max = nbt.getDouble("spell4_weapon_sword_count_max");
			spell4_weapon_sword_level = nbt.getDouble("spell4_weapon_sword_level");
			tick = nbt.getDouble("tick");
			user_book_flying_tick = nbt.getDouble("user_book_flying_tick");
			user_book_item_open = nbt.getBoolean("user_book_item_open");
			user_book_place = nbt.getBoolean("user_book_place");
			user_book_posX = nbt.getDouble("user_book_posX");
			user_book_posY = nbt.getDouble("user_book_posY");
			user_book_posZ = nbt.getDouble("user_book_posZ");
			user_DELETE_THIS = nbt.getString("user_DELETE_THIS");
			user = nbt.getBoolean("user");
			spell4_ability_cooldown_max = nbt.getDouble("spell4_ability_cooldown_max");
			spell4_ability_duration_max = nbt.getDouble("spell4_ability_duration_max");
		}

		public void markSyncDirty() {
			_syncDirty = true;
		}
	}

	public record PlayerVariablesSyncMessage(PlayerVariables data) implements CustomPacketPayload {
		public static final Type<PlayerVariablesSyncMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(TanscomplexmagicMod.MODID, "player_variables_sync"));
		public static final StreamCodec<RegistryFriendlyByteBuf, PlayerVariablesSyncMessage> STREAM_CODEC = StreamCodec
				.of((RegistryFriendlyByteBuf buffer, PlayerVariablesSyncMessage message) -> buffer.writeNbt(message.data().serializeNBT(buffer.registryAccess())), (RegistryFriendlyByteBuf buffer) -> {
					PlayerVariablesSyncMessage message = new PlayerVariablesSyncMessage(new PlayerVariables());
					message.data.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
					return message;
				});

		@Override
		public Type<PlayerVariablesSyncMessage> type() {
			return TYPE;
		}

		public static void handleData(final PlayerVariablesSyncMessage message, final IPayloadContext context) {
			if (context.flow() == PacketFlow.CLIENTBOUND && message.data != null) {
				context.enqueueWork(() -> context.player().getData(PLAYER_VARIABLES).deserializeNBT(context.player().registryAccess(), message.data.serializeNBT(context.player().registryAccess()))).exceptionally(e -> {
					context.connection().disconnect(Component.literal(e.getMessage()));
					return null;
				});
			}
		}
	}
}