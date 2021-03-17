library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity debounce is
	port(Clk: in std_logic;
	     Data_in: in std_logic;
	     Data_out: out std_logic);
end debounce;

architecture Behavioral of debounce is
signal temp_out: std_logic_vector(2 downto 0);

begin
										   
process(Clk, Data_in)
begin 
	if(Rising_Edge(Clk)) then 
		temp_out(2) <= Data_in;
		temp_out(1) <= temp_out(2);
		temp_out(0) <= temp_out(1);	 
	end if;
end process;

Data_out <= temp_out(2) and temp_out(1) and (not temp_out(0));	

end architecture;
