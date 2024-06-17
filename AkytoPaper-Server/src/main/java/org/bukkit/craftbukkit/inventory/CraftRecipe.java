package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.CraftingManager;
import net.minecraft.server.RecipesFurnace;
import org.bukkit.inventory.Recipe;

public interface CraftRecipe extends Recipe {
    void addToCraftingManager(CraftingManager craftingManager, RecipesFurnace recipesFurnace);
}
