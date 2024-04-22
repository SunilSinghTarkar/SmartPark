package com.smartpark.model;



public class ParkingSlot {

	    private int id;
	    private int slotNumber;
	    private Vehicle vehicle;
	    private boolean isOccupied;
	    
	    
	    public ParkingSlot() {
			
		}
	    
		public ParkingSlot(int id, int slotNumber, Vehicle vehicle, boolean isOccupied) {
			super();
			this.id = id;
			this.slotNumber = slotNumber;
			this.vehicle = vehicle;
			this.isOccupied = isOccupied;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getSlotNumber() {
			return slotNumber;
		}
		public void setSlotNumber(int slotNumber) {
			this.slotNumber = slotNumber;
		}
		public Vehicle getVehicle() {
			return vehicle;
		}
		public void setVehicle(Vehicle vehicle) {
			this.vehicle = vehicle;
		}
		public boolean isOccupied() {
			return isOccupied;
		}
		public void setOccupied(boolean isOccupied) {
			this.isOccupied = isOccupied;
		}

		@Override
		public String toString() {
			return "ParkingSlot [id=" + id + ", slotNumber=" + slotNumber + ", vehicle=" + vehicle + ", isOccupied="
					+ isOccupied + "]";
		}
	    
	    
	    
	    
}
