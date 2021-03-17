library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity filtering is
	port(Clk: in std_logic;
	     Data_in: in std_logic;
	     Data_out: out std_logic);
end filtering;

architecture Behavioral of filtering is
signal temp_out: std_logic_vector(7 downto 0);

begin
										   
process(Clk, Data_in)
begin 
	if(Rising_Edge(Clk)) then 
		temp_out(7) <= Data_in;
		temp_out(6 downto 0) <= temp_out(7 downto 1);	 
	end if;
end process;

Data_out <= '1' when temp_out = x"FF" else '0';	

end architecture;

