package io.github.paradoxicalblock.storycraft.main;

import io.github.paradoxicalblock.storycraft.entity.SocialVillagerFemale;
import io.github.paradoxicalblock.storycraft.entity.SocialVillagerMale;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.registry.Registry;

public class StoryCraft implements ModInitializer {
	
	static FabricEntityTypeBuilder<SocialVillagerMale> builder = FabricEntityTypeBuilder.create(EntityCategory.CREATURE,SocialVillagerMale::new).size(0.5F, 1.95f).trackable(64, 3);
	static FabricEntityTypeBuilder<SocialVillagerFemale> builderf = FabricEntityTypeBuilder.create(EntityCategory.CREATURE,SocialVillagerFemale::new).size(0.5F, 1.85f).trackable(64, 3);
	public static final EntityType<SocialVillagerMale> SOCIAL_VILLAGER_MALE = Registry.register(Registry.ENTITY_TYPE,"storycraft:social_villager_male", builder.build());
	public static final EntityType<SocialVillagerFemale> SOCIAL_VILLAGER_FEMALE = Registry.register(Registry.ENTITY_TYPE,"storycraft:social_villager_female", builderf.build());
	public static final Item SOCIAL_VILLAGER_MALE_EGG = Registry.register(Registry.ITEM, "storycraft:social_villager_male_egg", new SpawnEggItem(SOCIAL_VILLAGER_MALE, 5636095, 170, new Item.Settings().itemGroup(ItemGroup.MISC)));
	public static final Item SOCIAL_VILLAGER_FEMALE_EGG = Registry.register(Registry.ITEM, "storycraft:social_villager_female_egg", new SpawnEggItem(SOCIAL_VILLAGER_FEMALE, 16733525, 11141120, new Item.Settings().itemGroup(ItemGroup.MISC)));
	@Override
	public void onInitialize() {

	}
}
