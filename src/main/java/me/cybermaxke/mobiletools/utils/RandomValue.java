/**
 * 
 * This software is part of the MobileTools
 * 
 * MobileTools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or 
 * any later version.
 * 
 * MobileTools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MobileTools. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package me.cybermaxke.mobiletools.utils;

import java.util.Random;

public class RandomValue {
	private Random random = new Random();
	private int min, max;

	public RandomValue(int min, int max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * Gets a random value between the min and the max.
	 * @return value
	 */
	public int getRandom() {
		return this.min + this.random.nextInt(this.max - this.min);
	}

	/**
	 * Gets the minimum value.
	 * @return value
	 */
	public int getMin() {
		return this.min;
	}

	/**
	 * Gets the maximum value.
	 * @return value
	 */
	public int getMax() {
		return this.max;
	}
}