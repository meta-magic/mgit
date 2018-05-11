package com.desire3d.mgit.enums;

public enum RepositoryStatusEnum {
		
		ADDED("ADDED"),
		CHANGED("CHANGED"),
		REMOVED("REMOVED"),
		MISSING("MISSING"),
		MODIFIED("MODIFIED"),
		CONFLICTING("CONFLICTING");

		private String value;

		private RepositoryStatusEnum(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}

		/**
		 * method to get the {@link RepositoryStatusEnum} corresponding to the value
		 *
		 * @param value real value
		 * @return RepositoryStatusEnum corresponding to the value
		 *
		 * @throws IllegalArgumentException
		 */
		public static RepositoryStatusEnum fromValue(String value) {
			if (value == null || "".equals(value)) {
				throw new IllegalArgumentException("Value cannot be null or empty!");
			}

			for (RepositoryStatusEnum enumEntry : RepositoryStatusEnum.values()) {
				if (enumEntry.toString().equals(value)) {
					return enumEntry;
				}
			}

			throw new IllegalArgumentException("Cannot create enum from " + value + " value!");
		}
	}