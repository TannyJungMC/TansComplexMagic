package tannyjung.tanscomplexmagic_core.outside;

import io.netty.buffer.Unpooled;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import tannyjung.tanscomplexmagic_core.Core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record NetworkManager (boolean is_core, String category, String name, CompoundTag extra) implements CustomPacketPayload {

    public static final Type<NetworkManager> type = new Type<>(ResourceLocation.fromNamespaceAndPath(Core.mod_id, "network"));

    public static final StreamCodec<RegistryFriendlyByteBuf, NetworkManager> stream = StreamCodec.of((RegistryFriendlyByteBuf buffer, NetworkManager data) -> {

        buffer.writeBoolean(data.is_core);
        buffer.writeUtf(data.category);
        buffer.writeUtf(data.name);
        buffer.writeNbt(data.extra);

    }, (RegistryFriendlyByteBuf buffer) -> new NetworkManager(buffer.readBoolean(), buffer.readUtf(), buffer.readUtf(), buffer.readNbt()));

    @Override
    public @NotNull Type<NetworkManager> type () {

        return type;

    }

    public static void receive (NetworkManager data, IPayloadContext context) {

        boolean is_core = data.is_core == true;
        String category = data.category;
        String name = data.name;
        CompoundTag extra = data.extra;

        Player player = context.player();
        Level level = player.level();
        boolean is_debug_side_client = false;

        if (level.isClientSide == true) {

            NetworkBuiltInClient.run(player, category, name, extra);
            is_debug_side_client = true;

        } else {

            NetworkBuiltInServer.run(level, player, category, name, extra, is_core == true);

        }

        Debug.add(data, is_debug_side_client, true);

    }

    private static NetworkManager getSender (boolean is_client, boolean is_core, String category, String name, CompoundTag extra) {

        NetworkManager network = new NetworkManager(is_core, category, name, extra);
        Debug.add(network, is_client, false);
        return network;

    }

    public static void runServer (String category, String name, CompoundTag extra) {

        PacketDistributor.sendToServer(getSender(true, false, category, name, extra));

    }

    public static void runClientCore (ServerPlayer player_server, String category, String name, CompoundTag extra) {

        PacketDistributor.sendToPlayer(player_server, getSender(false, true, category, name, extra));

    }

    public static void runServerCore (String category, String name, CompoundTag extra) {

        PacketDistributor.sendToServer(getSender(true, true, category, name, extra));

    }

    public static class Debug {

        private static String timestamp = "";
        private static int total_send = 0;
        private static int total_receive = 0;

        public static void restart () {

            String description = "This place for network debugging. It shows all network sending and receiving in my handcode systems since last restart, so I can check if there is something wrong in my network. You can also see how much my network use your internet when in a server, hope it looks fine. You may need to turn on developer mode to see them here.\n";

            if (Core.developer_mode == true) {

                description = description + "\nRecording Started at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")) + "\n\n";

            }

            FileManager.writeTXT(Core.path_folder_config + "/debug_network.txt", description, false);
            timestamp = TimeUtils.getTimestampCurrent();
            total_send = 0;
            total_receive = 0;

        }

        public static void add (NetworkManager data, boolean is_client, boolean is_receiving) {

            if (Core.developer_mode == false) {

                return;

            }

            int size = new FriendlyByteBuf(Unpooled.buffer()).writeNbt(data.extra).readableBytes();
            String client_server = "";

            if (is_client == true) {

                client_server = "Client";

            } else {

                client_server = "Server";

            }

            String send_receive = "";
            int total = 0;

            if (is_receiving == true) {

                send_receive = "Receive";
                total_receive = total_receive + size;
                total = total_receive;

            } else {

                send_receive = "Send";
                total_send = total_send + size;
                total = total_send;

            }

            FileManager.writeTXT(Core.path_folder_config + "/debug_network.txt", "[" + TimeUtils.convertSecondToReadable(TimeUtils.getInterval(timestamp)) + "] " + client_server + " " + send_receive + " -> " + OutsideUtils.Calculation.convertByteToDigitalStorageUnit(total) + " (+" + OutsideUtils.Calculation.convertByteToDigitalStorageUnit(size) + ") -> " + data.category + "/" + data.name + " -> " + data.extra + "\n", true);

        }

    }

}