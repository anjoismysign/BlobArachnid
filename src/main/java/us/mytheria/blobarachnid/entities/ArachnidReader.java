package us.mytheria.blobarachnid.entities;

import com.heledron.spideranimation.Gait;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import us.mytheria.blobarachnid.BlobArachnid;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArachnidReader {
    private static ArachnidReader instance;
    private final BlobArachnid plugin;

    private ArachnidReader(BlobArachnid plugin) {
        this.plugin = plugin;
    }

    public static ArachnidReader initialize(@Nullable BlobArachnid plugin) {
        if (instance == null) {
            if (plugin == null)
                throw new IllegalArgumentException("'plugin' cannot be null");
            instance = new ArachnidReader(plugin);
        }
        return instance;
    }

    public static ArachnidReader getInstance() {
        return initialize(null);
    }

    @NotNull
    public List<BlockDisplayOperator> blockDisplayOperators(@NotNull ConfigurationSection section,
                                                            @NotNull String path) {
        Objects.requireNonNull(section, "'section' cannot be null");
        List<BlockDisplayOperator> list = new ArrayList<>();
        section.getKeys(false).forEach(key -> {
            ConfigurationSection subSection = section.getConfigurationSection(key);
            if (subSection == null)
                return;
            BlockDisplayOperator blockDisplayOperator = BlockDisplayOperator
                    .READ(subSection, path, plugin);
            list.add(blockDisplayOperator);
        });
        return list;
    }

    @NotNull
    public List<ItemDisplayOperator> itemDisplayOperators(@NotNull ConfigurationSection section,
                                                          @NotNull String path) {
        Objects.requireNonNull(section, "'section' cannot be null");
        List<ItemDisplayOperator> list = new ArrayList<>();
        section.getKeys(false).forEach(key -> {
            ConfigurationSection subSection = section.getConfigurationSection(key);
            if (subSection == null)
                return;
            ItemDisplayOperator itemDisplayOperator = ItemDisplayOperator
                    .READ(subSection, path, plugin);
            list.add(itemDisplayOperator);
        });
        return list;
    }

    @NotNull
    public Gait gait(@NotNull ConfigurationSection section) {
        Objects.requireNonNull(section, "'section' cannot be null");
        Gait gait = new Gait();
        if (section.isDouble("walkSpeed")) {
            double walkSpeed = section.getDouble("walkSpeed");
            gait.setWalkSpeed(walkSpeed);
        }
        if (section.isDouble("legSpeed")) {
            double legSpeed = section.getDouble("legSpeed");
            gait.setLegSpeed(legSpeed);
        }
        if (section.isDouble("walkAcceleration")) {
            double walkAcceleration = section.getDouble("walkAcceleration");
            gait.setWalkAcceleration(walkAcceleration);
        }
        if (section.isDouble("rotateSpeed")) {
            double rotateSpeed = section.getDouble("rotateSpeed");
            gait.setRotateSpeed(rotateSpeed);
        }
        if (section.isDouble("legLiftHeight")) {
            double legLiftHeight = section.getDouble("legLiftHeight");
            gait.setLegLiftHeight(legLiftHeight);
        }
        if (section.isDouble("legDropDistance")) {
            double legDropDistance = section.getDouble("legDropDistance");
            gait.setLegDropDistance(legDropDistance);
        }
        if (section.isDouble("legStationaryTriggerDistance")) {
            double legStationaryTriggerDistance = section.getDouble("legStationaryTriggerDistance");
            gait.setLegStationaryTriggerDistance(legStationaryTriggerDistance);
        }
        if (section.isDouble("legMovingTriggerDistance")) {
            double legMovingTriggerDistance = section.getDouble("legMovingTriggerDistance");
            gait.setLegMovingTriggerDistance(legMovingTriggerDistance);
        }
        if (section.isDouble("legDiscomfortDistance")) {
            double legDiscomfortDistance = section.getDouble("legDiscomfortDistance");
            gait.setLegDiscomfortDistance(legDiscomfortDistance);
        }
        if (section.isDouble("gravityAcceleration")) {
            double gravityAcceleration = section.getDouble("gravityAcceleration");
            gait.setGravityAcceleration(gravityAcceleration);
        }
        if (section.isDouble("airDragCoefficient")) {
            double airDragCoefficient = section.getDouble("airDragCoefficient");
            gait.setAirDragCoefficient(airDragCoefficient);
        }
        if (section.isDouble("bounceFactor")) {
            double bounceFactor = section.getDouble("bounceFactor");
            gait.setBounceFactor(bounceFactor);
        }
        if (section.isDouble("bodyHeightCorrectionAcceleration")) {
            double bodyHeightCorrectionAcceleration = section.getDouble("bodyHeightCorrectionAcceleration");
            gait.setBodyHeightCorrectionAcceleration(bodyHeightCorrectionAcceleration);
        }
        if (section.isDouble("bodyHeightCorrectionFactor")) {
            double bodyHeightCorrectionFactor = section.getDouble("bodyHeightCorrectionFactor");
            gait.setBodyHeightCorrectionFactor(bodyHeightCorrectionFactor);
        }
        if (section.isBoolean("applyGravity")) {
            boolean applyGravity = section.getBoolean("applyGravity");
            gait.setApplyGravity(applyGravity);
        }
        if (section.isDouble("bodyHeight")) {
            double bodyHeight = section.getDouble("bodyHeight");
            gait.setBodyHeight(bodyHeight);
        }
        if (section.isBoolean("legScanGround")) {
            boolean legScanGround = section.getBoolean("legScanGround");
            gait.setLegScanGround(legScanGround);
        }
        if (section.isBoolean("legAlwaysCanMove")) {
            boolean legAlwaysCanMove = section.getBoolean("legAlwaysCanMove");
            gait.setLegAlwaysCanMove(legAlwaysCanMove);
        }
        if (section.isBoolean("legScanAlternativeGround")) {
            boolean legScanAlternativeGround = section.getBoolean("legScanAlternativeGround");
            gait.setLegScanAlternativeGround(legScanAlternativeGround);
        }
        if (section.isBoolean("legApplyScanHeightBias")) {
            boolean legApplyScanHeightBias = section.getBoolean("legApplyScanHeightBias");
            gait.setLegApplyScanHeightBias(legApplyScanHeightBias);
        }
        if (section.isDouble("legStraightenHeight")) {
            double legStraightenHeight = section.getDouble("legStraightenHeight");
            gait.setLegStraightenHeight(legStraightenHeight);
        }
        if (section.isBoolean("legNoStraighten")) {
            boolean legNoStraighten = section.getBoolean("legNoStraighten");
            gait.setLegNoStraighten(legNoStraighten);
        }
        if (section.isDouble("legSegmentLength")) {
            double legSegmentLength = section.getDouble("legSegmentLength");
            gait.setLegSegmentLength(legSegmentLength);
        }
        if (section.isInt("legSegmentCount")) {
            int legSegmentCount = section.getInt("legSegmentCount");
            gait.setLegSegmentCount(legSegmentCount);
        }
        return gait;
    }
}
